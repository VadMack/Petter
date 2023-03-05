package ru.gortea.petter.di.network

import retrofit2.Retrofit

interface NetworkComponent {
    fun getRetrofit(): Retrofit
}
