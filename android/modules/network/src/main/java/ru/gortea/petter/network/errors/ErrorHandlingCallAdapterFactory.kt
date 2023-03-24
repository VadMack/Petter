package ru.gortea.petter.network.errors

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import ru.gortea.petter.auth.controller.LogoutController
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


internal class ErrorHandlingCallAdapterFactory(
    private val logoutController: LogoutController
) : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java || returnType !is ParameterizedType) {
            return null
        }

        val delegate = retrofit.nextCallAdapter(this, returnType, annotations)
        return ErrorHandlingCallAdapter(logoutController, delegate as CallAdapter<Any, Call<Any>>)
    }

    private class ErrorHandlingCallAdapter<R>(
        private val logoutController: LogoutController,
        private val delegateAdapter: CallAdapter<R, Call<R>>
    ) : CallAdapter<R, Call<R>> by delegateAdapter {

        override fun adapt(call: Call<R>): Call<R> {
            return delegateAdapter.adapt(ErrorHandlingCall(logoutController, call))
        }
    }
}
