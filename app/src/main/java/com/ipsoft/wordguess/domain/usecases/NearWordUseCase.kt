package com.ipsoft.wordguess.domain.usecases

import com.ipsoft.wordguess.data.entities.response.NearWordResponse
import com.ipsoft.wordguess.domain.repository.Repository
import com.ipsoft.wordguess.domain.usecases.NearWordUseCase.Params
import javax.inject.Inject

class NearWordUseCase
@Inject constructor(private val repository: Repository) :
    UseCase<NearWordResponse, Params>() {

    override suspend fun run(params: Params) = repository.approximateWord(params.word)

    data class Params(val word: String)

}