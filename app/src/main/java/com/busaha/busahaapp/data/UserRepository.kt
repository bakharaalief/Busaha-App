package com.busaha.busahaapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.busaha.busahaapp.data.remote.retorift.ApiService
import com.busaha.busahaapp.domain.entity.UserLogin
import com.busaha.busahaapp.domain.entity.UserRegister
import com.busaha.busahaapp.domain.repository.IUserRepository

class UserRepository(
    private val apiService: ApiService
) : IUserRepository {

    override fun loginUser(email: String, pass: String): LiveData<Result<UserLogin>> = liveData {
        emit(Result.Loading)

        try {
            val response = apiService.loginUser(email, pass)

            if (response.body()?.error == false) {
                val data = UserLogin(
                    response.body()?.loginResult?.userID!!,
                    response.body()?.loginResult?.username!!,
                )
                emit(Result.Success(data))
            }
            else if(response.body()?.error == true){
                emit(Result.Error("Email atau Password anda salah"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    override fun registerUser(
        name: String,
        email: String,
        pass: String,
        dob: String,
        gender: Char,
        status: String
    ): LiveData<Result<UserRegister>> = liveData {
        emit(Result.Loading)

        try {
            val response = apiService.registerUser(name, email, pass, dob, gender, status)

            if (response.body()?.error == "false") {
                val data = UserRegister(response.body()?.message!!)
                emit(Result.Success(data))
            } else {
                emit(Result.Error("Email anda sudah terdaftar"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }
}