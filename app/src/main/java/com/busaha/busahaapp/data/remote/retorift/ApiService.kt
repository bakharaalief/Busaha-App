package com.busaha.busahaapp.data.remote.retorift

import com.busaha.busahaapp.data.remote.response.*
import retrofit2.Response
import retrofit2.http.*

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

    @GET("test")
    suspend fun getTest(): Response<TestResponse>

    @GET("test/{questionId}")
    suspend fun getTestOption(@Path("questionId") questionId: Int): Response<TestOptionResponse>

    @GET("user/{id}")
    suspend fun detailUser(
        @Path("id") id: String
    ): Response<UserDetailResponse>

}