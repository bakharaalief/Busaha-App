package com.busaha.busahaapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("Error")
	val error: String,

	@field:SerializedName("message")
	val message: String
)
