package com.example.loginregister.API

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetroFitClient {
    private var linstance: Retrofit? = null
    fun getInstance(): Retrofit {
        if (linstance == null)
            linstance = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/") // 10.0.2.2 is localhost on emulator
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return linstance!!
    }
}