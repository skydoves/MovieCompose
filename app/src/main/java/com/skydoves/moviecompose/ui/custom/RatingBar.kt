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

package com.skydoves.moviecompose.ui.custom

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.moviecompose.ui.theme.purple200
import kotlin.math.cos
import kotlin.math.sin

/**
 * Open Source References: https://gist.github.com/vitorprado/0ae4ad60c296aefafba4a157bb165e60
 */

@Composable
fun RatingBar(
  rating: Float,
  modifier: Modifier = Modifier,
  color: Color = purple200
) {
  Row(modifier = modifier.wrapContentSize()) {
    (1..5).forEach { step ->
      val stepRating = when {
        rating > step -> 1f
        step.rem(rating) < 1 -> rating - (step - 1f)
        else -> 0f
      }
      RatingStar(stepRating, color)
    }
  }
}

@Composable
private fun RatingStar(
  rating: Float,
  ratingColor: Color = purple200,
  backgroundColor: Color = Color.Gray
) {
  BoxWithConstraints(
    modifier = Modifier
      .fillMaxHeight()
      .aspectRatio(1f)
      .clip(starShape)
  ) {
    Canvas(modifier = Modifier.size(maxHeight)) {
      drawRect(
        brush = SolidColor(backgroundColor),
        size = Size(
          height = size.height * 1.4f,
          width = size.width * 1.4f
        ),
        topLeft = Offset(
          x = -(size.width * 0.1f),
          y = -(size.height * 0.1f)
        )
      )
      if (rating > 0) {
        drawRect(
          brush = SolidColor(ratingColor),
          size = Size(
            height = size.height * 1.1f,
            width = size.width * rating
          )
        )
      }
    }
  }
}

private val starShape = GenericShape { size, _ ->
  addPath(starPath(size.height))
}

private val starPath = { size: Float ->
  Path().apply {
    val outerRadius: Float = size / 1.8f
    val innerRadius: Double = outerRadius / 2.5
    var rot: Double = Math.PI / 2 * 3
    val cx: Float = size / 2
    val cy: Float = size / 20 * 11
    var x: Float = cx
    var y: Float = cy
    val step = Math.PI / 5

    moveTo(cx, cy - outerRadius)
    repeat(5) {
      x = (cx + cos(rot) * outerRadius).toFloat()
      y = (cy + sin(rot) * outerRadius).toFloat()
      lineTo(x, y)
      rot += step

      x = (cx + cos(rot) * innerRadius).toFloat()
      y = (cy + sin(rot) * innerRadius).toFloat()
      lineTo(x, y)
      rot += step
    }
    close()
  }
}

@Preview
@Composable
fun RatingBarPreview() {
  Column(
    Modifier
      .fillMaxSize()
      .background(Color.White)
  ) {
    RatingBar(
      3.5f,
      modifier = Modifier.height(20.dp)
    )
  }
}
