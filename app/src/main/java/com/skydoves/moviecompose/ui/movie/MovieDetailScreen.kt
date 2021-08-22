/*
 * Designed and developed by 2021 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.skydoves.moviecompose.ui.movie

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.palette.graphics.Palette
import com.google.accompanist.flowlayout.FlowRow
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.palette.BitmapPalette
import com.skydoves.moviecompose.R
import com.skydoves.moviecompose.models.Keyword
import com.skydoves.moviecompose.models.Review
import com.skydoves.moviecompose.models.Video
import com.skydoves.moviecompose.models.entities.Movie
import com.skydoves.moviecompose.network.Api
import com.skydoves.moviecompose.network.compose.NetworkImage
import com.skydoves.moviecompose.ui.custom.AppBarWithArrow
import com.skydoves.moviecompose.ui.custom.RatingBar
import com.skydoves.moviecompose.ui.theme.background
import com.skydoves.moviecompose.ui.theme.purple200
import com.skydoves.whatif.whatIfNotNullOrEmpty

@Composable
fun MovieDetailScreen(
  posterId: Long,
  viewModel: MovieDetailViewModel,
  pressOnBack: () -> Unit
) {
  val movie: Movie? by viewModel.movieFlow.collectAsState(initial = null)

  LaunchedEffect(key1 = posterId) {
    viewModel.fetchMovieDetailsById(posterId)
  }

  Column(
    modifier = Modifier
      .verticalScroll(rememberScrollState())
      .background(background)
      .fillMaxSize(),
  ) {

    AppBarWithArrow(movie?.title, pressOnBack)

    MovieDetailHeader(viewModel)

    MovieDetailVideos(viewModel)

    MovieDetailSummary(viewModel)

    MovieDetailReviews(viewModel)

    Spacer(modifier = Modifier.height(24.dp))
  }
}

@Composable
private fun MovieDetailHeader(
  viewModel: MovieDetailViewModel
) {
  val movie: Movie? by viewModel.movieFlow.collectAsState(initial = null)

  Column {

    var palette by remember { mutableStateOf<Palette?>(null) }
    NetworkImage(
      networkUrl = Api.getBackdropPath(movie?.backdrop_path),
      circularReveal = CircularReveal(duration = 300),
      shimmerParams = null,
      bitmapPalette = BitmapPalette {
        palette = it
      },
      modifier = Modifier
        .height(280.dp)
    )

    Spacer(modifier = Modifier.height(25.dp))

    Text(
      text = movie?.title ?: "",
      style = MaterialTheme.typography.h5,
      color = Color.White,
      textAlign = TextAlign.Center,
      maxLines = 2,
      overflow = TextOverflow.Ellipsis,
      fontWeight = FontWeight.Bold,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp)
    )

    Spacer(modifier = Modifier.height(6.dp))

    Text(
      text = "Release Date: ${movie?.release_date}",
      style = MaterialTheme.typography.body1,
      color = Color.White,
      textAlign = TextAlign.Center,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
      fontWeight = FontWeight.Bold,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp)
    )

    Spacer(modifier = Modifier.height(8.dp))

    RatingBar(
      rating = (movie?.vote_average ?: 0f) / 2f,
      color = Color(palette?.vibrantSwatch?.rgb ?: 0),
      modifier = Modifier
        .height(15.dp)
        .align(Alignment.CenterHorizontally)
    )
  }
}

@Composable
private fun MovieDetailVideos(
  viewModel: MovieDetailViewModel
) {
  val videos by viewModel.videoListFlow.collectAsState(listOf())

  videos.whatIfNotNullOrEmpty {

    Column {

      Spacer(modifier = Modifier.height(23.dp))

      Text(
        text = stringResource(R.string.trailers),
        style = MaterialTheme.typography.h6,
        color = Color.White,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 15.dp)
      )

      Spacer(modifier = Modifier.height(12.dp))

      LazyRow(
        modifier = Modifier
          .padding(horizontal = 15.dp)
      ) {

        items(items = videos) { video ->

          VideoThumbnail(video)

          Spacer(modifier = Modifier.width(12.dp))
        }
      }
    }
  }
}

@Composable
private fun VideoThumbnail(
  video: Video
) {
  val context = LocalContext.current

  Surface(
    shape = RoundedCornerShape(8.dp),
    elevation = 8.dp,
  ) {

    ConstraintLayout(
      modifier = Modifier
        .width(150.dp)
        .height(100.dp)
        .clickable(
          onClick = {
            val playVideoIntent =
              Intent(Intent.ACTION_VIEW, Uri.parse(Api.getYoutubeVideoPath(video.key)))
            context.startActivity(playVideoIntent)
          }
        )
    ) {
      val (thumbnail, icon, box, title) = createRefs()

      var palette by remember { mutableStateOf<Palette?>(null) }
      NetworkImage(
        networkUrl = Api.getYoutubeThumbnailPath(video.key),
        modifier = Modifier
          .fillMaxSize()
          .constrainAs(thumbnail) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
          },
        bitmapPalette = BitmapPalette {
          palette = it
        }
      )

      Image(
        bitmap = ImageBitmap.imageResource(R.drawable.icon_youtube),
        contentDescription = null,
        modifier = Modifier
          .width(30.dp)
          .height(20.dp)
          .constrainAs(icon) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
          }
      )

      Crossfade(
        targetState = palette,
        modifier = Modifier
          .height(25.dp)
          .constrainAs(box) {
            bottom.linkTo(parent.bottom)
          }
      ) {

        Box(
          modifier = Modifier
            .background(Color(it?.darkVibrantSwatch?.rgb ?: 0))
            .alpha(0.7f)
            .fillMaxSize()
        )
      }

      Text(
        text = video.name,
        style = MaterialTheme.typography.body2,
        textAlign = TextAlign.Center,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
          .fillMaxWidth()
          .alpha(0.85f)
          .padding(horizontal = 8.dp)
          .constrainAs(title) {
            top.linkTo(box.top)
            bottom.linkTo(box.bottom)
          }
      )
    }
  }
}

@Composable
private fun MovieDetailSummary(
  viewModel: MovieDetailViewModel
) {
  val movie: Movie? by viewModel.movieFlow.collectAsState(initial = null)
  val keywords by viewModel.keywordListFlow.collectAsState(listOf())

  keywords.whatIfNotNullOrEmpty {

    Column {

      Spacer(modifier = Modifier.height(23.dp))

      Text(
        text = stringResource(R.string.summary),
        style = MaterialTheme.typography.h6,
        color = Color.White,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 15.dp)
      )

      Spacer(modifier = Modifier.height(12.dp))

      Text(
        text = movie?.overview ?: "",
        style = MaterialTheme.typography.body1,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 15.dp)
      )

      Spacer(modifier = Modifier.height(15.dp))

      FlowRow {

        it.forEach {

          Keyword(it)
        }
      }
    }
  }
}

@Composable
private fun Keyword(keyword: Keyword) {
  Surface(
    shape = RoundedCornerShape(32.dp),
    elevation = 8.dp,
    color = purple200,
    modifier = Modifier.padding(8.dp)
  ) {

    Text(
      text = keyword.name,
      style = MaterialTheme.typography.body1,
      color = Color.White,
      fontWeight = FontWeight.Bold,
      modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 4.dp)
    )
  }
}

@Composable
private fun MovieDetailReviews(
  viewModel: MovieDetailViewModel
) {
  val reviews by viewModel.reviewListFlow.collectAsState(listOf())

  reviews.whatIfNotNullOrEmpty {

    Column {

      Spacer(modifier = Modifier.height(23.dp))

      Text(
        text = stringResource(R.string.reviews),
        style = MaterialTheme.typography.h6,
        color = Color.White,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 15.dp)
      )

      Column {

        reviews.forEach {

          Review(it)
        }
      }
    }
  }
}

@Composable
private fun Review(
  review: Review
) {
  var expanded: Boolean by remember { mutableStateOf(false) }

  Column {

    Spacer(modifier = Modifier.height(12.dp))

    Text(
      text = review.author,
      style = MaterialTheme.typography.body1,
      color = Color.White,
      maxLines = 1,
      fontWeight = FontWeight.Bold,
      overflow = TextOverflow.Ellipsis,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 15.dp)
    )

    Spacer(modifier = Modifier.height(12.dp))

    if (expanded) {
      Text(
        text = review.content,
        style = MaterialTheme.typography.body2,
        color = Color.White,
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 15.dp)
          .clickable { expanded = !expanded }
      )
    } else {
      Text(
        text = review.content,
        style = MaterialTheme.typography.body2,
        color = Color.White,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 15.dp)
          .clickable { expanded = !expanded }
      )
    }
  }
}
