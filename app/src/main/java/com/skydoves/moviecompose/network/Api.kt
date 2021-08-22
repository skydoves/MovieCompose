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

package com.skydoves.moviecompose.network

object Api {
  const val BASE_URL = "https://api.themoviedb.org/"
  private const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342"
  private const val BASE_BACKDROP_PATH = "https://image.tmdb.org/t/p/w780"
  private const val YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v="
  private const val YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/"
  const val PAGING_SIZE = 20

  @JvmStatic
  fun getPosterPath(posterPath: String?): String {
    return BASE_POSTER_PATH + posterPath
  }

  @JvmStatic
  fun getBackdropPath(backdropPath: String?): String {
    return BASE_BACKDROP_PATH + backdropPath
  }

  @JvmStatic
  fun getYoutubeVideoPath(videoPath: String?): String {
    return YOUTUBE_VIDEO_URL + videoPath
  }

  @JvmStatic
  fun getYoutubeThumbnailPath(thumbnailPath: String?): String {
    return "$YOUTUBE_THUMBNAIL_URL$thumbnailPath/default.jpg"
  }
}
