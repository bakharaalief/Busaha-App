package com.busaha.busahaapp.presentation.business_test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.busaha.busahaapp.domain.model.Answer
import com.busaha.busahaapp.domain.use_case.TestUseCase
import kotlinx.coroutines.launch

class TestViewModel(private val testUseCase: TestUseCase) : ViewModel() {

    fun getTest() = testUseCase.getTest()

    fun getTestOption(questionId: Int) = testUseCase.getTestOption(questionId)

    fun saveAnswer(answer: Answer) {
        viewModelScope.launch {
            testUseCase.saveTestOption(answer)
        }
    }

    fun isAnswerSaved(idQuestion: Int) = testUseCase.isAnswerSaved(idQuestion)

    fun updateAnswer(answer: Answer) {
        viewModelScope.launch {
            testUseCase.updateTestOption(answer)
        }
    }

    fun getAnswerSaved(idQuestion: Int) = testUseCase.getAnswerSaved(idQuestion)

    fun deleteAllAnswer() {
        viewModelScope.launch {
            testUseCase.deleteAllAnswer()
        }
    }

    fun getCountAnswer() = testUseCase.getCountAnswer()
}