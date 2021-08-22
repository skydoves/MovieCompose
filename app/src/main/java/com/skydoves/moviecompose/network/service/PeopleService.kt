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

import com.skydoves.moviecompose.models.network.PeopleResponse
import com.skydoves.moviecompose.models.network.PersonDetail
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PeopleService {
  /**
   * [People Popular](https://developers.themoviedb.org/3/people/get-popular-people)
   *
   * Get the list of popular people on TMDb. This list updates daily.
   *
   * @param [page] Specify the page of results to query.
   *
   * @return [PeopleResponse] response
   */
  @GET("/3/person/popular?language=en")
  suspend fun fetchPopularPeople(@Query("page") page: Int): ApiResponse<PeopleResponse>

  /**
   * [Person Detail](https://developers.themoviedb.org/3/people/get-person-details)
   *
   * Get the primary person details by id.
   *
   * @para [id] Specify the id of results to query.
   *
   * @return [PersonDetail] response
   */
  @GET("/3/person/{person_id}")
  suspend fun fetchPersonDetail(@Path("person_id") id: Long): ApiResponse<PersonDetail>
}
