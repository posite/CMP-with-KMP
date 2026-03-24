package com.example.simple.util

const val NETWORK_EXCEPTION_BODY_IS_NULL = "result body is null"

suspend fun <T : Any, R : Any> handleKtorApi(
    execute: suspend () -> Result<T>,
    mapper: (T) -> R
): DataResult<R> {

    return try {
        val response = execute()
        val body = response.getOrNull()
        if (response.isSuccess) {
            body?.let {
                DataResult.Success(mapper(it))
            } ?: run {
                throw NullPointerException(NETWORK_EXCEPTION_BODY_IS_NULL)
            }
        } else {
            getFailDataKtorResult(body, response)
        }
    } catch (e: Exception) {
        DataResult.Error(e)
    }
}



private fun <T : Any> getFailDataKtorResult(body: T?, response: Result<T>) = body?.let {
    DataResult.Fail(statusCode = response.hashCode(), message = it.toString())
} ?: run {
    DataResult.Fail(statusCode = response.hashCode(), message = response.toString())
}