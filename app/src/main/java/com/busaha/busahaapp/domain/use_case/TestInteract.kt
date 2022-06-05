package com.busaha.busahaapp.domain.use_case

import androidx.lifecycle.LiveData
import com.busaha.busahaapp.data.Result
import com.busaha.busahaapp.data.local.entity.AnswerEntity
import com.busaha.busahaapp.domain.model.Answer
import com.busaha.busahaapp.domain.model.Questions
import com.busaha.busahaapp.domain.model.QuestionsOption
import com.busaha.busahaapp.domain.repository.ITestRepository

class TestInteract(private val testRepository: ITestRepository) : TestUseCase {

    override fun getTest(): LiveData<Result<Questions>> = testRepository.getTest()

    override fun getTestOption(questionId: Int): LiveData<Result<QuestionsOption>> =
        testRepository.getTestOption(questionId)

    override suspend fun saveTestOption(answer: Answer) = testRepository.saveTestOption(answer)

    override fun getAllAnswerOption(): LiveData<List<AnswerEntity>> =
        testRepository.getAllAnswerOption()

    override fun isAnswerSaved(idQuestion: Int): LiveData<Boolean> =
        testRepository.isAnswerSaved(idQuestion)

    override suspend fun updateTestOption(answer: Answer) = testRepository.updateTestOption(answer)

    override suspend fun deleteAllAnswer() = testRepository.deleteAllAnswer()

    override fun getAnswerSaved(idQuestion: Int): LiveData<AnswerEntity> =
        testRepository.getAnswerSaved(idQuestion)

    override fun getCountAnswer(): LiveData<Int> = testRepository.getCountAnswer()
}