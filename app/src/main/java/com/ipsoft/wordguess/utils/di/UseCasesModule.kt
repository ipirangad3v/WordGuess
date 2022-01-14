package com.ipsoft.wordguess.utils.di

import com.ipsoft.wordguess.domain.repository.Repository
import com.ipsoft.wordguess.domain.usecases.GetRandomWordUseCase
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
    fun createGetRandomWordUseCase(repository: Repository): GetRandomWordUseCase {
        return GetRandomWordUseCase(repository)
    }


}