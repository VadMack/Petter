package ru.gortea.petter.network

import retrofit2.Retrofit

inline fun<reified T> Retrofit.createApi(): T {
    return create(T::class.java)
}
