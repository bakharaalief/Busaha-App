package com.busaha.busahaapp.domain.model

data class Questions(
    val count: Int,
    val listQuestion: List<Question>
)

data class Question(
    val id: Int,
    val question: String
)
