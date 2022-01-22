package com.ipsoft.wordguess.domain.usecases

import com.ipsoft.wordguess.data.datasource.remote.entities.request.ValidateWordResponse
import com.ipsoft.wordguess.domain.repository.RemoteRepository
import com.ipsoft.wordguess.domain.usecases.ValidateWordUseCase.*
import javax.inject.Inject

class ValidateWordUseCase
@Inject constructor(private val remoteRepository: RemoteRepository) :
    UseCase<ValidateWordResponse, Params>() {

    override suspend fun run(params: Params) = remoteRepository.validateWord(params.word)

    data class Params(val word: String)
}