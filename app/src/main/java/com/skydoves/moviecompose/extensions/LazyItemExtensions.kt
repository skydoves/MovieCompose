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

package com.skydoves.moviecompose.extensions

import androidx.compose.foundation.lazy.LazyGridScope
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import com.skydoves.moviecompose.network.Api
import kotlinx.coroutines.flow.StateFlow

inline fun <T> LazyGridScope.paging(
  items: List<T>,
  currentIndexFlow: StateFlow<Int>,
  threshold: Int = 4,
  pageSize: Int = Api.PAGING_SIZE,
  crossinline fetch: () -> Unit,
  crossinline itemContent: @Composable LazyItemScope.(item: T) -> Unit,
) {
  val currentIndex = currentIndexFlow.value

  itemsIndexed(items) { index, item ->

    itemContent(item)

    if ((index + threshold + 1) >= pageSize * (currentIndex - 1)) {
      fetch()
    }
  }
}
