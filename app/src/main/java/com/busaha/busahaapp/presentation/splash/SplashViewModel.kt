package com.busaha.busahaapp.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.busaha.busahaapp.data.user_pref.UserModel
import com.busaha.busahaapp.data.user_pref.UserPreference

class SplashViewModel(private val pref: UserPreference) : ViewModel() {

    fun getUserData(): LiveData<UserModel> = pref.getUser().asLiveData()
}