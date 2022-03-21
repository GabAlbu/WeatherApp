package com.example.weatherapp2

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {

    companion object {

        private lateinit var retrofit: Retrofit
        private fun getRetrofitInstance(): Retrofit {
            val httpClient = OkHttpClient.Builder()

            retrofit = Retrofit.Builder()

                .baseUrl("http://api.weatherstack.com/")
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit
        }

        fun <S> createService(serviceClass: Class<S>): S {
            return getRetrofitInstance().create(serviceClass)

        }
    }
}