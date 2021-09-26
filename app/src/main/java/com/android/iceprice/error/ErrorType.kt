package com.android.iceprice.error

enum class ErrorType {
    /**
     * An [IOException] occurred while communicating to the server.
     */
    NETWORK,

    /**
     * A non-2xx HTTP status code was received from the server.
     */
    HTTP,

    /**
     * An internal error occurred while attempting to execute a request. It is best practice to
     * re-throw this exception so your application crashes.
     */
    UNEXPECTED
}