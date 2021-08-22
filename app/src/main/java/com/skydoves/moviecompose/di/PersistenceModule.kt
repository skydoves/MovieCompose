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

package com.skydoves.moviecompose.di

import android.content.Context
import androidx.room.Room
import com.skydoves.moviecompose.persistence.AppDatabase
import com.skydoves.moviecompose.persistence.MovieDao
import com.skydoves.moviecompose.persistence.PeopleDao
import com.skydoves.moviecompose.persistence.TvDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

  @Provides
  @Singleton
  fun provideRoomDataBase(@ApplicationContext context: Context): AppDatabase {
    return Room
      .databaseBuilder(context, AppDatabase::class.java, "MovieCompose.db")
      .allowMainThreadQueries()
      .build()
  }

  @Provides
  @Singleton
  fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
    return appDatabase.movieDao()
  }

  @Provides
  @Singleton
  fun provideTvDao(appDatabase: AppDatabase): TvDao {
    return appDatabase.tvDao()
  }

  @Provides
  @Singleton
  fun providePeopleDao(appDatabase: AppDatabase): PeopleDao {
    return appDatabase.peopleDao()
  }
}
