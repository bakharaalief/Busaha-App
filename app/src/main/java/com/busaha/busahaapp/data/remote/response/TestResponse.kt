package com.busaha.busahaapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class TestResponse(

	@field:SerializedName("Message")
	val message: String,

	@field:SerializedName("Error")
	val error: Boolean,

	@field:SerializedName("Questions")
	val questions: List<QuestionsItem>,

	@field:SerializedName("Count")
	val count: List<CountItem>
)

data class CountItem(

	@field:SerializedName("count(*)")
	val count: Int
)

data class QuestionsItem(

	@field:SerializedName("question")
	val question: String,

	@field:SerializedName("id")
	val id: Int
)
