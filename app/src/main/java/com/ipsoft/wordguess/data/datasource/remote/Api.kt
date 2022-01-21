package com.ipsoft.wordguess.data.datasource.remote

import com.ipsoft.wordguess.data.entities.request.ValidateWordResponse
import com.ipsoft.wordguess.data.entities.request.WordRequest
import com.ipsoft.wordguess.data.entities.response.WordResponse
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @POST(GET_RANDOM_WORD)
    fun getRandomWord(@Body request: WordRequest): Call<WordResponse>

    @GET
    fun validateWord(@Url url: String): Call<ValidateWordResponse>

    companion object {
        const val GET_RANDOM_WORD = "GetRandomWords"
    }

}