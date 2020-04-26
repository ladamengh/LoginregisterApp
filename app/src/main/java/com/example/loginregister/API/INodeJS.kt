package com.example.loginregister.API

import com.example.loginregister.model.Course
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface INodeJS {

    @POST("register")
    @FormUrlEncoded
    fun registerUser(@Field("email") email: String,
                     @Field("name") name: String,
                     @Field("password") password: String): Observable<String>

    @POST("login")
    @FormUrlEncoded
    fun loginUser(@Field("email") email: String,
                  @Field("password") password: String): Observable<String>

    @get:GET("courses")
    val coursesList:Observable<List<Course>>

    @POST("search")
    @FormUrlEncoded
    fun searchCourse(@Field("search") searchQuery: String): Observable<List<Course>>
}