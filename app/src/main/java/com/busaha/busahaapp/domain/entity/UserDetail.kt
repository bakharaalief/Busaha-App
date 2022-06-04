package com.busaha.busahaapp.domain.entity

data class UserDetail (
    val id: String,
    val name: String,
    val email: String,
    val dob: String,
    val gender: Char,
    val status: String
)