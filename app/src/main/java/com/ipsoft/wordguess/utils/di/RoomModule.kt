package com.ipsoft.wordguess.utils.di

import android.content.Context
import androidx.room.Room
import com.ipsoft.wordguess.data.datasource.local.room.ScoreDao
import com.ipsoft.wordguess.data.datasource.local.room.ScoreDatabase
import com.ipsoft.wordguess.domain.core.constants.DB_NAME
import com.ipsoft.wordguess.domain.repository.LocalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    fun provideChannelDao(appDatabase: ScoreDatabase): ScoreDao {
        return appDatabase.scoreDao()
    }

    @Provides
    @Singleton
    fun provideRoomInstance(@ApplicationContext appContext: Context): ScoreDatabase {
        return Room.databaseBuilder(
            appContext, ScoreDatabase::class.java, DB_NAME
        ).build()
    }
    @Provides
    @Singleton
    fun provideLocalRepository(dataSource: LocalRepository.LocalRepositoryImpl): LocalRepository =
        dataSource


}