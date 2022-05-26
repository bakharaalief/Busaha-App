package com.busaha.busahaapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("loginResult")
	val loginResult: LoginResult,

	@field:SerializedName("Message")
	val message: String,

	@field:SerializedName("Error")
	val error: Boolean
)

data class LoginResult(

	@field:SerializedName("accessToken")
	val accessToken: String,

	@field:SerializedName("userID")
	val userID: String,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("refreshToken")
	val refreshToken: String
)
