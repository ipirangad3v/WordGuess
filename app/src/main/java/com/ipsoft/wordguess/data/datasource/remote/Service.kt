package com.ipsoft.wordguess.data.datasource.remote

import com.ipsoft.wordguess.data.entities.request.WordRequest
import com.ipsoft.wordguess.data.entities.response.WordResponse
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Service
@Inject constructor(retrofit: Retrofit) : Api {
    private val api by lazy { retrofit.create(Api::class.java) }

    override fun getRandomWord(request: WordRequest): Call<WordResponse> =
        api.getRandomWord(request)

}