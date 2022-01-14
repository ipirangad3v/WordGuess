package com.ipsoft.wordguess.data.entities.request


import com.google.gson.annotations.SerializedName

data class Filter(
    @SerializedName("Contains")
    val contains: String = "",
    @SerializedName("EndsWith")
    val endsWith: String = "",
    @SerializedName("MaxNumberOfLetters")
    val maxNumberOfLetters: Int = 5,
    @SerializedName("MinNumberOfLetters")
    val minNumberOfLetters: Int = 5,
    @SerializedName("StartsWith")
    val startsWith: String = ""
)