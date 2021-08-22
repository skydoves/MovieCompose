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

package com.skydoves.moviecompose.ui.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.moviecompose.ui.theme.purple200

@Composable
fun AppBarWithArrow(
  title: String?,
  pressOnBack: () -> Unit
) {
  TopAppBar(
    elevation = 6.dp,
    backgroundColor = purple200,
    modifier = Modifier.height(58.dp)
  ) {
    Row {
      Spacer(modifier = Modifier.width(10.dp))

      Image(
        imageVector = Icons.Filled.ArrowBack,
        colorFilter = ColorFilter.tint(Color.White),
        contentDescription = null,
        modifier = Modifier
          .align(Alignment.CenterVertically)
          .clickable {
            pressOnBack()
          }
      )

      Spacer(modifier = Modifier.width(12.dp))

      Text(
        modifier = Modifier
          .padding(8.dp)
          .align(Alignment.CenterVertically),
        text = title ?: "",
        color = Color.White,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
      )
    }
  }
}
