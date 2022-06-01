package com.busaha.busahaapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.busaha.busahaapp.data.local.entity.AnswerEntity
import com.busaha.busahaapp.data.local.room.AnswerDao
import com.busaha.busahaapp.data.remote.retorift.ApiService
import com.busaha.busahaapp.domain.entity.*
import com.busaha.busahaapp.domain.repository.ITestRepository

class TestRepository(private val apiService: ApiService, private val answerDao: AnswerDao) :
    ITestRepository {

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

    override suspend fun saveTestOption(answer: Answer) {
        answerDao.insert(
            AnswerEntity(
                answer.idQuestion,
                answer.idAnswer,
                answer.index
            )
        )
    }

    override fun getAllAnswerOption(): LiveData<List<AnswerEntity>> = answerDao.getAnswers()

    override fun isAnswerSaved(idQuestion: Int): LiveData<Boolean> =
        answerDao.isAnswerSaved(idQuestion)

    override suspend fun updateTestOption(answer: Answer) = answerDao.update(
        AnswerEntity(
            answer.idQuestion,
            answer.idAnswer,
            answer.index
        )
    )

    override suspend fun deleteAllAnswer() = answerDao.deleteAllRecord()

    override fun getAnswerSaved(idQuestion: Int): LiveData<AnswerEntity> =
        answerDao.getAnswerSaved(idQuestion)
}