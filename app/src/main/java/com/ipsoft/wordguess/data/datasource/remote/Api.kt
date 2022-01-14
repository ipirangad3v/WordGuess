package com.ipsoft.wordguess.data.datasource.remote

import com.ipsoft.wordguess.data.entities.response.WordResponse
import retrofit2.Call
import retrofit2.http.GET

interface Api {

    @GET(GET_RANDOM_WORD)
    fun getRandomWord(): Call<WordResponse>

    companion object {
        const val GET_RANDOM_WORD = "random"
    }

}