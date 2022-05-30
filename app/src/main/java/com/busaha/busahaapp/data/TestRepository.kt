package com.busaha.busahaapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.busaha.busahaapp.data.remote.retorift.ApiService
import com.busaha.busahaapp.domain.entity.Question
import com.busaha.busahaapp.domain.entity.QuestionOption
import com.busaha.busahaapp.domain.entity.Questions
import com.busaha.busahaapp.domain.entity.QuestionsOption
import com.busaha.busahaapp.domain.repository.ITestRepository

class TestRepository(private val apiService: ApiService) : ITestRepository {

    override fun getTest(): LiveData<Result<Questions>> = liveData {
        emit(Result.Loading)

        try {
            val response = apiService.getTest()

            if (response.body()?.error == false) {

                val data = Questions(
                    response.body()?.count?.get(0)?.count!!,
                    response.body()?.questions?.map { Question(it.id, it.question) }!!
                )
                emit(Result.Success(data))
            } else if (response.body()?.error == true) {
                emit(Result.Error("Tidak bisa menarik soal"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    override fun getTestOption(questionId: Int): LiveData<Result<QuestionsOption>> = liveData {
        emit(Result.Loading)

        try {
            val response = apiService.getTestOption(questionId)

            if (response.body()?.error == false) {

                val data = QuestionsOption(
                    response.body()?.answer?.map {
                        QuestionOption(
                            it.answerId,
                            it.answer,
                            it.indeks
                        )
                    }!!
                )
                emit(Result.Success(data))
            } else if (response.body()?.error == true) {
                emit(Result.Error("Tidak bisa menarik option soal"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }
}