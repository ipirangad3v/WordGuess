package com.ipsoft.wordguess.data.datasource.local.room

import androidx.room.*
import com.ipsoft.wordguess.data.datasource.local.room.entities.Score

@Dao
interface ScoreDao {

    @Query("SELECT * FROM score")
    fun loadAll(): List<Score>

    @Insert
    fun insert(score: Score)

    @Delete
    fun delete(score: Score)

    @Update
    fun update(score: Score)
}