package com.ipsoft.wordguess.domain.usecases

import com.ipsoft.wordguess.data.datasource.local.room.entities.Score
import com.ipsoft.wordguess.domain.core.exception.Failure
import com.ipsoft.wordguess.domain.core.function.Either
import com.ipsoft.wordguess.domain.repository.LocalRepository
import com.ipsoft.wordguess.domain.usecases.SaveScoreUseCase.Params
import javax.inject.Inject

class SaveScoreUseCase @Inject constructor(private val localRepository: LocalRepository) :
    UseCase<Unit, Params>() {

    data class Params(val score: Score)

    override suspend fun run(params: Params): Either<Failure, Unit> =
        localRepository.saveScore(params.score)
}
