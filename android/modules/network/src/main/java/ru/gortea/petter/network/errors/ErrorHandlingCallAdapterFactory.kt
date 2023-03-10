package ru.gortea.petter.network.errors

import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


internal class ErrorHandlingCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java || returnType !is ParameterizedType) {
            return null
        }

        val responseType: Type = getParameterUpperBound(0, returnType)
        return ErrorHandlingCallAdapter<Any>(responseType)
    }

    private class ErrorHandlingCallAdapter<R>(private val responseType: Type) : CallAdapter<R, Call<R>> {

        override fun responseType(): Type {
            return responseType
        }

        override fun adapt(call: Call<R>): Call<R> {
            return ErrorHandlingCall(call)
        }

    }
}
