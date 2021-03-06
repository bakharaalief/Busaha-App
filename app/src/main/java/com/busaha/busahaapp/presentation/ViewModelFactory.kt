package com.busaha.busahaapp.presentation

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.busaha.busahaapp.data.user_pref.UserPreference
import com.busaha.busahaapp.di.Injection
import com.busaha.busahaapp.domain.use_case.TestUseCase
import com.busaha.busahaapp.domain.use_case.TrendUseCase
import com.busaha.busahaapp.domain.use_case.UserUseCase
import com.busaha.busahaapp.presentation.business_result.BusinessResultViewModel
import com.busaha.busahaapp.presentation.business_test.TestViewModel
import com.busaha.busahaapp.presentation.business_trend.BusinessTrendViewModel
import com.busaha.busahaapp.presentation.login.LoginViewModel
import com.busaha.busahaapp.presentation.main.ProfileViewModel
import com.busaha.busahaapp.presentation.register.RegisterViewModel
import com.busaha.busahaapp.presentation.splash.SplashViewModel

class ViewModelFactory(
    private val userUseCase: UserUseCase,
    private val trendUseCase: TrendUseCase,
    private val testUseCase: TestUseCase,
    private val pref: UserPreference,
) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> SplashViewModel(
                pref
            ) as T
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(
                userUseCase, pref
            ) as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel(
                userUseCase
            ) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel(
                userUseCase, pref
            ) as T
            modelClass.isAssignableFrom(BusinessTrendViewModel::class.java) -> BusinessTrendViewModel(
                trendUseCase
            ) as T
            modelClass.isAssignableFrom(TestViewModel::class.java) -> TestViewModel(
                testUseCase
            ) as T
            modelClass.isAssignableFrom(BusinessResultViewModel::class.java) -> BusinessResultViewModel(
                testUseCase
            ) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context, pref: DataStore<Preferences>): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideUserUseCase(),
                    Injection.provideTrendUseCase(),
                    Injection.provideTestUseCase(context),
                    UserPreference.getInstance(pref),
                )
            }.also { instance = it }
    }
}