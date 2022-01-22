package com.ipsoft.wordguess.domain.usecases

import com.ipsoft.wordguess.data.datasource.remote.entities.request.WordRequest
import com.ipsoft.wordguess.data.datasource.remote.entities.response.WordResponse
import com.ipsoft.wordguess.domain.repository.RemoteRepository
import com.ipsoft.wordguess.domain.usecases.RandomWordUseCase.Params
import javax.inject.Inject

class RandomWordUseCase
@Inject constructor(private val remoteRepository: RemoteRepository) :
    UseCase<WordResponse, Params>() {

    override suspend fun run(params: Params) = remoteRepository.getRandomWord(params.wordRequest)

    data class Params(val wordRequest: WordRequest)

}
