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

package com.skydoves.moviecompose.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.skydoves.moviecompose.models.entities.Tv

@Dao
interface TvDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertTv(tvs: List<Tv>)

  @Update
  suspend fun updateTv(tv: Tv)

  @Query("SELECT * FROM Tv WHERE id = :id_")
  suspend fun getTv(id_: Long): Tv?

  @Query("SELECT * FROM Tv WHERE page = :page_")
  suspend fun getTvList(page_: Int): List<Tv>
}
