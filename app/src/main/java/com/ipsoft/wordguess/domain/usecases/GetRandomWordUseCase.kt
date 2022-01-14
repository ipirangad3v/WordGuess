package com.ipsoft.wordguess.domain.usecases

import com.ipsoft.wordguess.data.entities.response.WordResponse
import com.ipsoft.wordguess.domain.repository.Repository
import com.ipsoft.wordguess.domain.usecases.GetRandomWordUseCase.Params
import javax.inject.Inject

class GetRandomWordUseCase
@Inject constructor(private val repository: Repository) :
    UseCase<WordResponse, Params>() {

    override suspend fun run(params: Params) = repository.getRandomWord()

    data class Params(val wordLength: Int)

}
