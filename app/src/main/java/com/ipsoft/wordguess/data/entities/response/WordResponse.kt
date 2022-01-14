package com.ipsoft.wordguess.data.entities.response


import com.google.gson.annotations.SerializedName

data class WordResponse(
    @SerializedName("sense")
    val sense: Int = 0,
    @SerializedName("wid")
    val wid: Int = 0,
    @SerializedName("word")
    val word: String = ""
)