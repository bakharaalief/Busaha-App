package com.busaha.busahaapp.presentation.business_test

import androidx.lifecycle.ViewModel
import com.busaha.busahaapp.domain.use_case.TestUseCase

class TestViewModel(private val testUseCase: TestUseCase) : ViewModel() {

    fun getTest() = testUseCase.getTest()

    fun getTestOption(questionId: Int) = testUseCase.getTestOption(questionId)
}