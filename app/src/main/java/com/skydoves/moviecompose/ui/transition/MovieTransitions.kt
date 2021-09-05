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

package com.skydoves.moviecompose.ui.transition

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

object MovieTransitions {

  fun enterTransition(
    duration: Int = 500
  ): EnterTransition {
    return slideInHorizontally(
      initialOffsetX = { 1000 },
      animationSpec = tween(
        durationMillis = duration,
        easing = FastOutSlowInEasing
      )
    ) + fadeIn(animationSpec = tween(durationMillis = duration))
  }

  fun exitTransition(
    duration: Int = 500
  ): ExitTransition {
    return slideOutHorizontally(
      targetOffsetX = { -1000 },
      animationSpec = tween(
        durationMillis = duration,
        easing = FastOutSlowInEasing
      )
    ) + fadeOut(animationSpec = tween(durationMillis = duration))
  }

  fun popEnterTransition(
    duration: Int = 500
  ): EnterTransition {
    return slideInHorizontally(
      initialOffsetX = { -1000 },
      animationSpec = tween(
        durationMillis = duration,
        easing = FastOutSlowInEasing
      )
    ) + fadeIn(animationSpec = tween(durationMillis = duration))
  }

  fun popExitTransition(
    duration: Int = 500
  ): ExitTransition {
    return slideOutHorizontally(
      targetOffsetX = { 1000 },
      animationSpec = tween(
        durationMillis = duration,
        easing = FastOutSlowInEasing
      )
    ) + fadeOut(animationSpec = tween(durationMillis = duration))
  }
}
