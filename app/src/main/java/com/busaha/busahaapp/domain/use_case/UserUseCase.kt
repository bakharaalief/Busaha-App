package com.busaha.busahaapp.domain.use_case

import androidx.lifecycle.LiveData
import com.busaha.busahaapp.data.Result
import com.busaha.busahaapp.domain.model.UserDetail
import com.busaha.busahaapp.domain.model.UserLogin
import com.busaha.busahaapp.domain.model.UserRegister

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

    fun getDetailUser(
        id: String
    ): LiveData<Result<UserDetail>>
}