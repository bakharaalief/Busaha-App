package com.busaha.busahaapp.presentation.business_result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.busaha.busahaapp.domain.use_case.TestUseCase
import kotlinx.coroutines.launch

class BusinessResultViewModel(private val testUseCase: TestUseCase) : ViewModel() {

    val getAllAnswerOption = testUseCase.getAllAnswerOption()

    fun deleteAllAnswer() {
        viewModelScope.launch {
            testUseCase.deleteAllAnswer()
        }
    }
}