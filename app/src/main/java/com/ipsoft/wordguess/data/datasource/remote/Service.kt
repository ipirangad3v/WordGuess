package com.ipsoft.wordguess.data.datasource.remote

import com.ipsoft.wordguess.data.entities.response.WordResponse
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Service
@Inject constructor(retrofit: Retrofit) : Api {
    private val api by lazy { retrofit.create(Api::class.java) }
    override fun getRandomWord(): Call<WordResponse> = api.getRandomWord()

}