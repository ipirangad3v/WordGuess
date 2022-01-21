package com.ipsoft.wordguess.utils.di

import com.ipsoft.wordguess.domain.repository.Repository
import com.ipsoft.wordguess.domain.usecases.NearWordUseCase
import com.ipsoft.wordguess.domain.usecases.RandomWordUseCase
import com.ipsoft.wordguess.domain.usecases.ValidateWordUseCase
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
    fun createGetRandomWordUseCase(repository: Repository): RandomWordUseCase {
        return RandomWordUseCase(repository)
    }

    @Provides
    @Singleton
    fun createValidateWordUseCase(repository: Repository): ValidateWordUseCase {
        return ValidateWordUseCase(repository)
    }
    @Provides
    @Singleton
    fun createNearWordUseCase(repository: Repository): NearWordUseCase {
        return NearWordUseCase(repository)
    }


}