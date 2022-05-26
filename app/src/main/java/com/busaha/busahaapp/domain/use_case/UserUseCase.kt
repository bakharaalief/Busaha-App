package com.busaha.busahaapp.domain.use_case

import androidx.lifecycle.LiveData
import com.busaha.busahaapp.data.Result
import com.busaha.busahaapp.domain.entity.UserLogin
import com.busaha.busahaapp.domain.entity.UserRegister

interface UserUseCase {
    fun loginUser(email: String, pass: String): LiveData<Result<UserLogin>>

    fun registerUser(
        name: String,
        email: String,
        pass: String,
        dob: String,
        gender: Char,
        status: String
    ): LiveData<Result<UserRegister>>
}