package com.weather.WeatherInfo.Contract

interface ContractInterface {

    interface View {
        fun initView()
    }

    interface Presenter {
        fun getTemprature(): String
        fun getLocation(): String
    }

    interface Model {
        fun getTemprature(): String
        fun getLocation(): String
    }

}