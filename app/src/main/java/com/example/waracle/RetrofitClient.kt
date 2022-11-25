package com.example.waracle

import com.example.waracle.api.api
import retrofit2.Retrofit
import retrofit2.Retrofit.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class RetrofitClient private constructor() {
    private val retrofit //retrofit object
            : Retrofit = Builder().baseUrl(base_url)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create()).build()

    init { //constructor
    }

    fun getapi(): api { //defining api function
        return retrofit.create(api::class.java)
    }

    companion object {
        //world wide cases
        private const val base_url = "https://gist.githubusercontent.com/" //base url

        @get:Synchronized
        var instance: RetrofitClient? = null
            get() {
                if (field == null) {
                    field = RetrofitClient()
                }
                return field
            }
            private set
    }
}