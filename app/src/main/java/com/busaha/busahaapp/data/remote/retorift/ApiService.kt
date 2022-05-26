package com.busaha.busahaapp.data.remote.retorift

import com.busaha.busahaapp.data.remote.response.LoginResponse
import com.busaha.busahaapp.data.remote.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("signin")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") pass: String
    ): Response<LoginResponse>

    @FormUrlEncoded
    @POST("signup")
    suspend fun registerUser(
        @Field("displayName") name: String,
        @Field("email") email: String,
        @Field("password") pass: String,
        @Field("dob") dob: String,
        @Field("gender") gender: Char,
        @Field("status") status: String
    ): Response<RegisterResponse>
}