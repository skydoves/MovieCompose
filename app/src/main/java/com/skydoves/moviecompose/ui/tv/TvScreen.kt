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

package com.skydoves.moviecompose.ui.tv

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.palette.graphics.Palette
import com.google.accompanist.insets.statusBarsPadding
import com.skydoves.landscapist.palette.BitmapPalette
import com.skydoves.moviecompose.extensions.paging
import com.skydoves.moviecompose.models.entities.Tv
import com.skydoves.moviecompose.models.network.NetworkState
import com.skydoves.moviecompose.models.network.onLoading
import com.skydoves.moviecompose.network.Api
import com.skydoves.moviecompose.network.compose.NetworkImage
import com.skydoves.moviecompose.ui.main.MainScreenHomeTab
import com.skydoves.moviecompose.ui.main.MainViewModel

@Composable
fun TvScreen(
  viewModel: MainViewModel,
  selectPoster: (MainScreenHomeTab, Long) -> Unit,
  lazyListState: LazyListState,
  modifier: Modifier = Modifier
) {
  val networkState: NetworkState by viewModel.tvLoadingState
  val tvs by viewModel.tvs

  LazyVerticalGrid(
    cells = GridCells.Fixed(2),
    state = lazyListState,
    modifier = modifier
      .statusBarsPadding()
      .background(MaterialTheme.colors.background)
  ) {

    paging(
      items = tvs,
      currentIndexFlow = viewModel.tvPageStateFlow,
      fetch = { viewModel.fetchNextTvPage() }
    ) {

      TvPoster(
        tv = it,
        selectPoster = selectPoster
      )
    }
  }

  networkState.onLoading {
    Box(
      modifier = Modifier.fillMaxSize()
    ) {

      CircularProgressIndicator(
        modifier = Modifier.align(Alignment.Center)
      )
    }
  }
}

@Composable
private fun TvPoster(
  tv: Tv,
  selectPoster: (MainScreenHomeTab, Long) -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier
      .fillMaxWidth()
      .height(290.dp)
      .clickable(
        onClick = {
          selectPoster(MainScreenHomeTab.TV, tv.id)
        }
      ),
    color = MaterialTheme.colors.onBackground
  ) {

    ConstraintLayout {
      val (image, box, title) = createRefs()

      var palette by remember { mutableStateOf<Palette?>(null) }
      NetworkImage(
        networkUrl = Api.getPosterPath(tv.poster_path),
        modifier = Modifier
          .height(240.dp)
          .constrainAs(image) {
            top.linkTo(parent.top)
          },
        bitmapPalette = BitmapPalette {
          palette = it
        }
      )

      Crossfade(
        targetState = palette,
        modifier = Modifier
          .height(50.dp)
          .constrainAs(box) {
            top.linkTo(image.bottom)
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
        text = tv.name,
        style = MaterialTheme.typography.body2,
        textAlign = TextAlign.Center,
        maxLines = 2,
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
