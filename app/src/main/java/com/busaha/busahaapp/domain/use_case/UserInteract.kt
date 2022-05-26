package com.busaha.busahaapp.domain.use_case

import androidx.lifecycle.LiveData
import com.busaha.busahaapp.data.Result
import com.busaha.busahaapp.domain.entity.UserLogin
import com.busaha.busahaapp.domain.entity.UserRegister
import com.busaha.busahaapp.domain.repository.IUserRepository

class UserInteract(private val userRepository: IUserRepository) :
    UserUseCase {

    override fun loginUser(email: String, pass: String): LiveData<Result<UserLogin>> =
        userRepository.loginUser(email, pass)

    override fun registerUser(
        name: String,
        email: String,
        pass: String,
        dob: String,
        gender: Char,
        status: String
    ): LiveData<Result<UserRegister>> =
        userRepository.registerUser(name, email, pass, dob, gender, status)
}