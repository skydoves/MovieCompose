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

package com.skydoves.moviecompose.repository

import androidx.annotation.WorkerThread
import com.skydoves.moviecompose.network.service.PeopleService
import com.skydoves.moviecompose.persistence.PeopleDao
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import timber.log.Timber

class PeopleRepository constructor(
  private val peopleService: PeopleService,
  private val peopleDao: PeopleDao
) : Repository {

  init {
    Timber.d("Injection PeopleRepository")
  }

  @WorkerThread
  fun loadPeople(page: Int, success: () -> Unit, error: () -> Unit) = flow {
    var people = peopleDao.getPeople(page)
    if (people.isEmpty()) {
      val response = peopleService.fetchPopularPeople(page)
      response.suspendOnSuccess {
        people = data.results
        people.forEach { it.page = page }
        peopleDao.insertPeople(people)
        emit(people)
      }.onError {
        error()
      }.onException { error() }
    } else {
      emit(people)
    }
  }.onCompletion { success() }.flowOn(Dispatchers.IO)

  @WorkerThread
  fun loadPersonDetail(id: Long, success: () -> Unit) = flow {
    val person = peopleDao.getPerson(id)
    var personDetail = person.personDetail
    if (personDetail == null) {
      val response = peopleService.fetchPersonDetail(id)
      response.suspendOnSuccess {
        personDetail = data
        person.personDetail = personDetail
        peopleDao.updatePerson(person)
        emit(personDetail)
      }
    } else {
      emit(personDetail)
    }
  }.onCompletion { success() }.flowOn(Dispatchers.IO)

  @WorkerThread
  fun loadPersonById(id: Long) = flow {
    val person = peopleDao.getPerson(id)
    emit(person)
  }.flowOn(Dispatchers.IO)
}
