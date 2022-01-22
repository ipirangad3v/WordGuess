package com.ipsoft.wordguess.domain.usecases

import com.ipsoft.wordguess.data.datasource.local.room.entities.Score
import com.ipsoft.wordguess.domain.core.exception.Failure
import com.ipsoft.wordguess.domain.core.function.Either
import com.ipsoft.wordguess.domain.repository.LocalRepository
import javax.inject.Inject

class GetScoreUseCase
@Inject constructor(private val localRepository: LocalRepository) :
    UseCase<List<Score>, UseCase.None>() {

    override suspend fun run(params: None): Either<Failure, List<Score>> =
        localRepository.getScore()
}