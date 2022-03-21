package com.example.weatherapp2


data class GetTemperatureResponse (val current:CurrentTemperature)

data class CurrentTemperature(val temperature:Int)