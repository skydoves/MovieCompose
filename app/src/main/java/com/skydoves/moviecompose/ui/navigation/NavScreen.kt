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

package com.skydoves.moviecompose.ui.navigation

import androidx.compose.runtime.Immutable

@Immutable
sealed class NavScreen(val route: String) {

  object Home : NavScreen("Home")

  object MovieDetails : NavScreen("MovieDetails") {

    const val routeWithArgument: String = "MovieDetails/{movieId}"

    const val argument0: String = "movieId"
  }

  object TvDetails : NavScreen("TvDetails") {

    const val routeWithArgument: String = "TvDetails/{tvId}"

    const val argument0: String = "tvId"
  }

  object PersonDetails : NavScreen("PersonDetails") {

    const val routeWithArgument: String = "PersonDetails/{personId}"

    const val argument0: String = "personId"
  }
}
