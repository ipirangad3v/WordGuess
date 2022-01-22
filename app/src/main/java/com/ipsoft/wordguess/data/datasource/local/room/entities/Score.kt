package com.ipsoft.wordguess.data.datasource.local.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Score(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "wins") val wins: Int,
    @ColumnInfo(name = "loses") val loses: Int,
    @ColumnInfo(name = "dropouts") val dropouts: Int
)