package com.ipsoft.wordguess.domain.core.exception

sealed class Failure {
    object NetworkConnection : Failure()
    object ServerError : Failure()

}