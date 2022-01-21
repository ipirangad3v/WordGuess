package com.ipsoft.wordguess.domain.usecases

import com.ipsoft.wordguess.data.entities.request.ValidateWordResponse
import com.ipsoft.wordguess.domain.repository.Repository
import com.ipsoft.wordguess.domain.usecases.ValidateWordUseCase.*
import javax.inject.Inject

class ValidateWordUseCase
@Inject constructor(private val repository: Repository) :
    UseCase<ValidateWordResponse, Params>() {

    override suspend fun run(params: Params) = repository.validateWord(params.word)

    data class Params(val word: String)
}