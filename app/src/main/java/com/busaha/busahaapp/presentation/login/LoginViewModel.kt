package com.busaha.busahaapp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.busaha.busahaapp.data.user_pref.UserModel
import com.busaha.busahaapp.data.user_pref.UserPreference
import com.busaha.busahaapp.domain.entity.UserLogin
import com.busaha.busahaapp.domain.use_case.UserUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userUseCase: UserUseCase,
    private val userPreference: UserPreference
) : ViewModel() {

    fun loginUser(email: String, pass: String) = userUseCase.loginUser(email, pass)

    fun saveUser(userLogin: UserLogin) {
        viewModelScope.launch {
            val data = UserModel(
                userLogin.localId,
                userLogin.displayName,
                true
            )
            userPreference.saveUser(data)
        }
    }
}