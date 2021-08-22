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

package com.skydoves.moviecompose.network.service

import com.skydoves.moviecompose.models.network.DiscoverMovieResponse
import com.skydoves.moviecompose.models.network.DiscoverTvResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TheDiscoverService {
  /**
   * [Movie Discover](https://developers.themoviedb.org/3/discover/movie-discover)
   *
   *  Discover movies by different types of data like average rating, number of votes, genres and certifications.
   *  You can get a valid list of certifications from the  method.
   *
   *  @param [page] Specify the page of results to query.
   *
   *  @return [DiscoverMovieResponse] response
   */
  @GET("/3/discover/movie?language=en&sort_by=popularity.desc")
  suspend fun fetchDiscoverMovie(@Query("page") page: Int): ApiResponse<DiscoverMovieResponse>

  /**
   * [Tv Discover](https://developers.themoviedb.org/3/discover/tv-discover)
   *
   *  Discover TV shows by different types of data like average rating, number of votes, genres, the network they aired on and air dates.
   *
   *  @param [page] Specify the page of results to query.
   *
   *  @return [DiscoverTvResponse] response
   */
  @GET("/3/discover/tv?language=en&sort_by=popularity.desc")
  suspend fun fetchDiscoverTv(@Query("page") page: Int): ApiResponse<DiscoverTvResponse>
}
