package com.ipsoft.wordguess.domain.repository

import com.ipsoft.wordguess.data.datasource.local.room.ScoreDao
import com.ipsoft.wordguess.data.datasource.local.room.entities.Score
import com.ipsoft.wordguess.domain.core.exception.Failure
import com.ipsoft.wordguess.domain.core.function.Either
import com.ipsoft.wordguess.domain.core.function.Either.Right
import javax.inject.Inject

interface LocalRepository {

    suspend fun saveScore(score: Score): Either<Failure, Unit>
    suspend fun getScore(): Either<Failure, List<Score>>
    suspend fun updateScore(score: Score): Either<Failure, Unit>


    class LocalRepositoryImpl @Inject constructor(private val scoreDao: ScoreDao) :
        LocalRepository {
        override suspend fun saveScore(score: Score): Either<Failure, Unit> =
            Right(scoreDao.insert(score))


        override suspend fun getScore(): Either<Failure, List<Score>> = Right(scoreDao.loadAll())
        override suspend fun updateScore(score: Score): Either<Failure, Unit> =
            Right(scoreDao.update(score))


    }

}