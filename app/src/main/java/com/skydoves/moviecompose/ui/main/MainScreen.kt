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

package com.skydoves.moviecompose.ui.main

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Tv
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.insets.ProvideWindowInsets
import com.skydoves.moviecompose.R
import com.skydoves.moviecompose.ui.movie.MovieDetailScreen
import com.skydoves.moviecompose.ui.navigation.NavScreen
import com.skydoves.moviecompose.ui.people.PersonDetailScreen
import com.skydoves.moviecompose.ui.theme.purple200
import com.skydoves.moviecompose.ui.tv.TvDetailScreen

@Composable
fun MainScreen() {
  val navController = rememberNavController()
  val tabStateHolder = HomeTabStateHolder(
    rememberLazyListState(),
    rememberLazyListState(),
    rememberLazyListState(),
  )

  ProvideWindowInsets {
    NavHost(navController = navController, startDestination = NavScreen.Home.route) {
      composable(
        route = NavScreen.Home.route,
        arguments = emptyList()
      ) {
        HomeTabScreen(
          viewModel = hiltViewModel(),
          tabStateHolder = tabStateHolder,
          selectItem = { tab, index ->
            when (tab) {
              MainScreenHomeTab.MOVIE -> navController.navigate("${NavScreen.MovieDetails.route}/$index")
              MainScreenHomeTab.TV -> navController.navigate("${NavScreen.TvDetails.route}/$index")
              MainScreenHomeTab.PERSON -> navController.navigate("${NavScreen.PersonDetails.route}/$index")
            }
          }
        )
      }
      composable(
        route = NavScreen.MovieDetails.routeWithArgument,
        arguments = listOf(
          navArgument(NavScreen.MovieDetails.argument0) { type = NavType.LongType }
        )
      ) { backStackEntry ->

        val posterId =
          backStackEntry.arguments?.getLong(NavScreen.MovieDetails.argument0)
            ?: return@composable

        MovieDetailScreen(posterId, hiltViewModel()) {
          navController.navigateUp()
        }
      }
      composable(
        route = NavScreen.TvDetails.routeWithArgument,
        arguments = listOf(
          navArgument(NavScreen.TvDetails.argument0) { type = NavType.LongType }
        )
      ) { backStackEntry ->

        val posterId = backStackEntry.arguments?.getLong(NavScreen.TvDetails.argument0)
          ?: return@composable

        TvDetailScreen(posterId, hiltViewModel()) {
          navController.navigateUp()
        }
      }
      composable(
        route = NavScreen.PersonDetails.routeWithArgument,
        arguments = listOf(
          navArgument(NavScreen.PersonDetails.argument0) { type = NavType.LongType }
        )
      ) { backStackEntry ->

        val personId =
          backStackEntry.arguments?.getLong(NavScreen.PersonDetails.argument0)
            ?: return@composable

        PersonDetailScreen(personId, hiltViewModel()) {
          navController.navigateUp()
        }
      }
    }
  }
}

@Preview
@Composable
fun MainAppBar() {
  TopAppBar(
    elevation = 6.dp,
    backgroundColor = purple200,
    modifier = Modifier.height(58.dp)
  ) {
    Text(
      modifier = Modifier
        .padding(8.dp)
        .align(Alignment.CenterVertically),
      text = stringResource(R.string.app_name),
      color = Color.White,
      fontSize = 18.sp,
      fontWeight = FontWeight.Bold
    )
  }
}

@Immutable
enum class MainScreenHomeTab(
  @StringRes val title: Int,
  val icon: ImageVector
) {
  MOVIE(R.string.menu_movie, Icons.Filled.Home),
  TV(R.string.menu_tv, Icons.Filled.Tv),
  PERSON(R.string.menu_person, Icons.Filled.Person);
}
