package com.android.iceprice.error

import android.content.res.Resources
import com.android.iceprice.R
import java.io.IOException
import retrofit2.HttpException
import retrofit2.Response

class IceException(
    private val errorType: ErrorType,
    private val response: Response<*>? = null,
    val httpCode: Int = 0,
    cause: Throwable? = null
) : RuntimeException(cause?.message, cause) {

    override val message: String?
        get() = when (errorType) {
            ErrorType.HTTP -> response?.message()

            ErrorType.NETWORK -> cause?.message

            ErrorType.UNEXPECTED -> cause?.message
        }

    fun getLocalizedMessage(resources: Resources): String? {
        return when (errorType) {
            ErrorType.NETWORK -> resources.getString(R.string.internet_error)
            else -> localizedMessage
        }
    }

    companion object {
        fun toHttpError(response: Response<*>?, httpCode: Int) =
            IceException(
                errorType = ErrorType.HTTP,
                response = response,
                httpCode = httpCode
            )

        fun toNetworkError(cause: Throwable) =
            IceException(
                errorType = ErrorType.NETWORK,
                cause = cause
            )

        fun toUnexpectedError(cause: Throwable) =
            IceException(
                errorType = ErrorType.UNEXPECTED,
                cause = cause
            )
    }
}

fun Throwable.toIcException(): IceException {
    return when (val throwable = this) {
        is IceException -> throwable

        is IOException -> IceException.toNetworkError(throwable)

        is HttpException -> {
            val response = throwable.response()
            val httpCode = throwable.code()

            if (response?.errorBody() == null) {
                return IceException.toHttpError(
                    httpCode = httpCode,
                    response = response
                )
            }

            IceException.toHttpError(
                response = response,
                httpCode = httpCode
            )
        }
        else -> IceException.toUnexpectedError(throwable)
    }
}