package com.ipsoft.wordguess.data.datasource.remote

import com.ipsoft.wordguess.data.entities.request.WordRequest
import com.ipsoft.wordguess.data.entities.response.WordResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST(GET_RANDOM_WORD)
    fun getRandomWord(@Body request: WordRequest): Call<WordResponse>

    companion object {
        const val GET_RANDOM_WORD = "GetRandomWords"
    }

}