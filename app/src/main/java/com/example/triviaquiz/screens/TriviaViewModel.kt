package com.example.triviaquiz.screens

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.triviaquiz.data.DataOrException
import com.example.triviaquiz.model.QuestionItem
import com.example.triviaquiz.repository.QuestionRepository
import com.example.triviaquiz.util.CheckConnectivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TriviaViewModel @Inject constructor(
    private val repository: QuestionRepository
): ViewModel() {

    val data: MutableState<DataOrException<
            ArrayList<QuestionItem>,
            Boolean,
            Exception>> = mutableStateOf(DataOrException(null, true, Exception("")))

    init {
        getAllQuestions()
    }

    fun checkConnectivity(context: Context): Boolean = CheckConnectivity.isInternetAvailable(context)

     private fun getAllQuestions() {
        viewModelScope.launch {
            data.value.loading = true
            data.value = repository.getAllQuestion()
            if (data.value.toString().isNotEmpty()) {
                data.value.loading = false
            }
        }
    }
}