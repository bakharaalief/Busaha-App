package com.busaha.busahaapp.presentation.register

import androidx.lifecycle.ViewModel
import com.busaha.busahaapp.domain.use_case.UserUseCase

class RegisterViewModel(private val userUseCase: UserUseCase) : ViewModel() {

    fun registerUser(
        name: String,
        email: String,
        pass: String,
        dob: String,
        gender: Char,
        status: String
    ) = userUseCase.registerUser(name, email, pass, dob, gender, status)
}