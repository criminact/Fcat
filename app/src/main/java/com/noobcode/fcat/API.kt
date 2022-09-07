package com.noobcode.fcat

import retrofit2.Call
import retrofit2.http.GET

interface API {
    @GET("/advice")
    fun fetchAdvice() : Call<AdviceObject>
}