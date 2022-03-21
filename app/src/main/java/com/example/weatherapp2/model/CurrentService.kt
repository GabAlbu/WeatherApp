package com.example.weatherapp2.model
import com.example.weatherapp2.GetTemperatureResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrentService {

    @GET("current?access_key=934184c6587c1ee909bc66fc15680891")
    fun list(@Query("query") city: String): Call<GetTemperatureResponse>

}