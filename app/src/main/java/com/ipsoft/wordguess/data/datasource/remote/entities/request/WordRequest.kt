package com.ipsoft.wordguess.data.datasource.remote.entities.request


import com.google.gson.annotations.SerializedName

data class WordRequest(
    @SerializedName("DictionaryName")
    val dictionaryName: String = "Palavras comuns (1000)",
    @SerializedName("Filter")
    val filter: Filter = Filter(),
    @SerializedName("LanguageName")
    val languageName: String = "Português",
    @SerializedName("NumberOfWords")
    val numberOfWords: Int = 1
)