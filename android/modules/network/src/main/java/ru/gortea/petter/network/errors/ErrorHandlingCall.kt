package ru.gortea.petter.network.errors

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class ErrorHandlingCall<T>(
    private val call: Call<T>
) : Call<T> by call {

    override fun enqueue(callback: Callback<T>) {
        call.enqueue(createCallback())
    }

    private fun createCallback(): Callback<T> {
        return object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) return

                when (response.code()) {
                    INTERNAL_ERROR -> throw PetterNetworkError.InternalError()
                    USER_ALREADY_EXISTS -> throw PetterNetworkError.UserAlreadyExists()
                    else -> throw PetterNetworkError.UnknownError()
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                throw PetterNetworkError.RequestError(t)
            }
        }
    }

    private companion object {
        private const val INTERNAL_ERROR = 500
        private const val USER_ALREADY_EXISTS = 400
    }
}

