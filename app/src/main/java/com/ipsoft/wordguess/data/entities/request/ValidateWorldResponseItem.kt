package com.ipsoft.wordguess.data.entities.request


import com.google.gson.annotations.SerializedName

data class ValidateWorldResponseItem(
    @SerializedName("creator")
    val creator: String = "",
    @SerializedName("deleted")
    val deleted: Int = 0,
    @SerializedName("deletor")
    val deletor: Any = Any(),
    @SerializedName("derived_from")
    val derivedFrom: Any = Any(),
    @SerializedName("last_revision")
    val lastRevision: Int = 0,
    @SerializedName("moderator")
    val moderator: Any = Any(),
    @SerializedName("normalized")
    val normalized: String = "",
    @SerializedName("revision_id")
    val revisionId: Int = 0,
    @SerializedName("sense")
    val sense: Int = 0,
    @SerializedName("timestamp")
    val timestamp: String = "",
    @SerializedName("word")
    var word: String = "",
    @SerializedName("word_id")
    val wordId: Int = 0,
    @SerializedName("xml")
    val xml: String = ""
)