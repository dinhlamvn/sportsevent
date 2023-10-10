package com.inspius.sportsevent.extensions

import retrofit2.HttpException

fun Throwable.parseHttpException(): Throwable {
    val httpException = this as? HttpException

    return httpException?.let { exception ->
        val response = exception.response() ?: return this
        val errorBody = response.errorBody() ?: return this
        val errorMessage = errorBody.string() ?: return this
        return Throwable(errorMessage)
    } ?: this
}