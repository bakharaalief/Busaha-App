package com.busaha.busahaapp.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.busaha.busahaapp.data.user_pref.UserModel
import com.busaha.busahaapp.data.user_pref.UserPreference
import com.busaha.busahaapp.domain.use_case.UserUseCase
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userUseCase: UserUseCase,
    private val userPreference: UserPreference
) : ViewModel() {

    fun getDetailUser(
        id: String
    ) = userUseCase.getDetailUser(id)

    fun getUserData(): LiveData<UserModel> = userPreference.getUser().asLiveData()


    fun signOut() {
        viewModelScope.launch {
            userPreference.logout()
        }
    }
}