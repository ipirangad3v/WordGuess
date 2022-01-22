package com.ipsoft.wordguess.domain.usecases

import com.ipsoft.wordguess.data.datasource.remote.entities.response.NearWordResponse
import com.ipsoft.wordguess.domain.repository.RemoteRepository
import com.ipsoft.wordguess.domain.usecases.NearWordUseCase.Params
import javax.inject.Inject

class NearWordUseCase
@Inject constructor(private val remoteRepository: RemoteRepository) :
    UseCase<NearWordResponse, Params>() {

    override suspend fun run(params: Params) = remoteRepository.approximateWord(params.word)

    data class Params(val word: String)

}