package com.busaha.busahaapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.busaha.busahaapp.data.user_pref.UserPreference
import kotlinx.coroutines.launch

class ProfileViewModel(private val userPreference: UserPreference) : ViewModel() {

    fun signOut() {
        viewModelScope.launch {
            userPreference.logout()
        }
    }
}