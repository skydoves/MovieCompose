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

package com.skydoves.moviecompose.models.network

import androidx.compose.runtime.Composable

enum class NetworkState {
  IDLE,
  LOADING,
  ERROR,
  SUCCESS
}

@Composable
fun NetworkState.onSuccess(block: @Composable () -> Unit): NetworkState {
  if (this == NetworkState.SUCCESS) {
    block()
  }
  return this
}

@Composable
fun NetworkState.onLoading(block: @Composable () -> Unit): NetworkState {
  if (this == NetworkState.LOADING) {
    block()
  }
  return this
}
