package com.ipsoft.wordguess.utils.di

import com.ipsoft.wordguess.domain.repository.LocalRepository
import com.ipsoft.wordguess.domain.repository.RemoteRepository
import com.ipsoft.wordguess.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCasesModule {

    @Provides
    @Singleton
    fun createGetRandomWordUseCase(remoteRepository: RemoteRepository): RandomWordUseCase {
        return RandomWordUseCase(remoteRepository)
    }

    @Provides
    @Singleton
    fun createValidateWordUseCase(remoteRepository: RemoteRepository): ValidateWordUseCase {
        return ValidateWordUseCase(remoteRepository)
    }

    @Provides
    @Singleton
    fun createNearWordUseCase(remoteRepository: RemoteRepository): NearWordUseCase {
        return NearWordUseCase(remoteRepository)
    }

    @Provides
    @Singleton
    fun getScoreUseCase(localRepository: LocalRepository): GetScoreUseCase {
        return GetScoreUseCase(localRepository)
    }

    @Provides
    @Singleton
    fun saveScoreUseCase(localRepository: LocalRepository): SaveScoreUseCase {
        return SaveScoreUseCase(localRepository)
    }


}