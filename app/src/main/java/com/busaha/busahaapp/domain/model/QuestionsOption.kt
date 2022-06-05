package com.busaha.busahaapp.domain.model

data class QuestionsOption(
    val listOption: List<QuestionOption>
)

data class QuestionOption(
    val answerId: Int,
    val answer: String,
    val index: Int
)
