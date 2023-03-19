package ru.gortea.petter.network.errors

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class ErrorHandlingCall<T>(
    private val call: Call<T>
) : Call<T> by call {

    override fun enqueue(callback: Callback<T>) {
        call.enqueue(createCallback(callback))
    }

    private fun createCallback(callback: Callback<T>): Callback<T> {
        return object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    callback.onResponse(call, response)
                    return
                }

                val error = when (response.code()) {
                    INTERNAL_ERROR -> PetterNetworkError.InternalError()
                    USER_ALREADY_EXISTS -> PetterNetworkError.UserAlreadyExists()
                    else -> PetterNetworkError.UnknownError()
                }

                callback.onFailure(call, error)
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                callback.onFailure(call, PetterNetworkError.RequestError(t))
            }
        }
    }

    override fun clone() = ErrorHandlingCall(call.clone())

    private companion object {
        private const val INTERNAL_ERROR = 500
        private const val USER_ALREADY_EXISTS = 400
    }
}

