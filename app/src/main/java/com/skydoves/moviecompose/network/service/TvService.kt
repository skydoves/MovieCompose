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

import com.skydoves.moviecompose.models.network.KeywordListResponse
import com.skydoves.moviecompose.models.network.ReviewListResponse
import com.skydoves.moviecompose.models.network.VideoListResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface TvService {
  /**
   * [Tv Videos](https://developers.themoviedb.org/3/tv/get-tv-keywords)
   *
   * Get the keywords that have been added to a TV show.
   *
   * @param [id] Specify the id of tv keywords.
   *
   * @return [VideoListResponse] response
   */
  @GET("/3/tv/{tv_id}/keywords")
  suspend fun fetchKeywords(@Path("tv_id") id: Long): ApiResponse<KeywordListResponse>

  /**
   * [Tv Videos](https://developers.themoviedb.org/3/tv/get-tv-videos)
   *
   * Get the videos that have been added to a TV show.
   *
   * @param [id] Specify the id of tv id.
   *
   * @return [VideoListResponse] response
   */
  @GET("/3/tv/{tv_id}/videos")
  suspend fun fetchVideos(@Path("tv_id") id: Long): ApiResponse<VideoListResponse>

  /**
   * [Tv Reviews](https://developers.themoviedb.org/3/tv/get-tv-reviews)
   *
   * Get the reviews for a TV show.
   *
   * @param [id] Specify the id of tv id.
   *
   * @return [ReviewListResponse] response
   */
  @GET("/3/tv/{tv_id}/reviews")
  suspend fun fetchReviews(@Path("tv_id") id: Long): ApiResponse<ReviewListResponse>
}
