package com.ipsoft.wordguess.domain.repository

import com.ipsoft.wordguess.data.datasource.remote.Service
import com.ipsoft.wordguess.data.entities.request.ValidateWordResponse
import com.ipsoft.wordguess.data.entities.request.WordRequest
import com.ipsoft.wordguess.data.entities.response.NearWordResponse
import com.ipsoft.wordguess.data.entities.response.WordResponse
import com.ipsoft.wordguess.domain.core.constants.NEAR_WORD_BASE_URL
import com.ipsoft.wordguess.domain.core.constants.VALIDATE_WORD_BASE_URL
import com.ipsoft.wordguess.domain.core.exception.Failure
import com.ipsoft.wordguess.domain.core.exception.Failure.NetworkConnection
import com.ipsoft.wordguess.domain.core.exception.Failure.ServerError
import com.ipsoft.wordguess.domain.core.function.Either
import com.ipsoft.wordguess.domain.core.function.Either.Left
import com.ipsoft.wordguess.domain.core.function.Either.Right
import com.ipsoft.wordguess.domain.core.plataform.NetworkHandler
import retrofit2.Call
import javax.inject.Inject

interface Repository {

    suspend fun getRandomWord(wordRequest: WordRequest): Either<Failure, WordResponse>
    suspend fun validateWord(word: String): Either<Failure, ValidateWordResponse>
    suspend fun approximateWord(word: String): Either<Failure, NearWordResponse>


    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: Service
    ) : Repository {

        override suspend fun getRandomWord(wordRequest: WordRequest): Either<Failure, WordResponse> {
            return when (networkHandler.isNetworkAvailable()) {
                true ->
                    request(
                        service.getRandomWord(wordRequest)

                    ) { it }


                false -> Left(NetworkConnection)
            }
        }

        override suspend fun validateWord(word: String): Either<Failure, ValidateWordResponse> {
            return when (networkHandler.isNetworkAvailable()) {
                true ->
                    request(
                        service.validateWord(VALIDATE_WORD_BASE_URL.format(word))

                    ) { it }


                false -> Left(NetworkConnection)
            }
        }

        override suspend fun approximateWord(word: String): Either<Failure, NearWordResponse> {
            return when (networkHandler.isNetworkAvailable()) {
                true ->
                    request(
                        service.nearWord(NEAR_WORD_BASE_URL.format(word))

                    ) { it }


                false -> Left(NetworkConnection)
            }
        }


    }

    fun <T, R> request(
        call: Call<T>,
        transform: (T) -> R,
    ): Either<Failure, R> {
        return try {
            val response = call.execute()
            val either = when (response.isSuccessful) {
                true -> Right(transform((response.body()!!)))
                false -> Left(ServerError)
            }
            either
        } catch (exception: Throwable) {
            Left(ServerError)
        }
    }
}
