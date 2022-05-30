package com.busaha.busahaapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class TestOptionResponse(

	@field:SerializedName("question")
	val question: List<QuestionItem>,

	@field:SerializedName("answer")
	val answer: List<AnswerItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class AnswerItem(

	@field:SerializedName("answer")
	val answer: String,

	@field:SerializedName("indeks")
	val indeks: Int,

	@field:SerializedName("answer_id")
	val answerId: Int,

	@field:SerializedName("question_id")
	val questionId: Int
)

data class QuestionItem(

	@field:SerializedName("question")
	val question: String,

	@field:SerializedName("id")
	val id: Int
)
