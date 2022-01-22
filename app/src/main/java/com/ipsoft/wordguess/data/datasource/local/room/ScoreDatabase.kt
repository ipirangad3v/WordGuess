package com.ipsoft.wordguess.data.datasource.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ipsoft.wordguess.data.datasource.local.room.entities.Score

@Database(entities = [Score::class], version = 1)
abstract class ScoreDatabase : RoomDatabase() {
    abstract fun scoreDao(): ScoreDao
}
