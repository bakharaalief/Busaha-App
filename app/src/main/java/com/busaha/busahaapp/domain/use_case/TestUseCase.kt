package com.busaha.busahaapp.domain.use_case

import androidx.lifecycle.LiveData
import com.busaha.busahaapp.data.Result
import com.busaha.busahaapp.data.local.entity.AnswerEntity
import com.busaha.busahaapp.domain.entity.Answer
import com.busaha.busahaapp.domain.entity.Questions
import com.busaha.busahaapp.domain.entity.QuestionsOption

interface TestUseCase {
    fun getTest(): LiveData<Result<Questions>>

    fun getTestOption(questionId: Int): LiveData<Result<QuestionsOption>>

    suspend fun saveTestOption(answer: Answer)

    fun getAllAnswerOption(): LiveData<List<AnswerEntity>>

    fun isAnswerSaved(idQuestion: Int): LiveData<Boolean>

    suspend fun updateTestOption(answer: Answer)

    suspend fun deleteAllAnswer()

    fun getAnswerSaved(idQuestion: Int): LiveData<AnswerEntity>
}