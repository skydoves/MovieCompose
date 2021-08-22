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

package com.skydoves.moviecompose.ui.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skydoves.moviecompose.models.network.PersonDetail
import com.skydoves.moviecompose.repository.PeopleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.shareIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PersonDetailViewModel @Inject constructor(
  private val peopleRepository: PeopleRepository
) : ViewModel() {

  private val personIdSharedFlow: MutableSharedFlow<Long> = MutableSharedFlow(replay = 1)

  val personFlow = personIdSharedFlow.flatMapLatest {
    peopleRepository.loadPersonById(it)
  }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)

  val personDetailFlow: Flow<PersonDetail?> = personIdSharedFlow.flatMapLatest {
    peopleRepository.loadPersonDetail(it) {
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)
  }

  init {
    Timber.d("Injection PersonDetailViewModel")
  }

  fun fetchPersonDetailsById(id: Long) = personIdSharedFlow.tryEmit(id)
}
