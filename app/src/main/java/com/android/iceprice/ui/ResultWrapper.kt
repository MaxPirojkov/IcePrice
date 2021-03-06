package com.android.iceprice.ui

/**
 * State Management for UI & Data.
 */
class ResultWrapper<T, E : Throwable> private constructor(
    val isLoading: Boolean,
    val data: T?,
    val error: E?
) {
    companion object {
        fun <T, E : Throwable> loading() = ResultWrapper<T, E>(true, null, null)
        fun <T, E : Throwable> success(data: T) = ResultWrapper<T, E>(false, data, null)
        fun <T, E : Throwable> failure(error: E) = ResultWrapper<T, E>(false, null, error)
    }

    val state: State
    var hasBeenHandled = false
        private set

    init {
        state = if (isLoading)
            State.LOADING
        else if (!isLoading && data != null)
            State.SUCCESS
        else State.FAILURE
    }

    fun getStateIfNotHandled(): State? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            state
        }
    }

    enum class State {
        LOADING,
        SUCCESS,
        FAILURE
    }
}