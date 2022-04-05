package com.example.triviaquiz.screens

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.triviaquiz.components.QuestionLayout
import com.example.triviaquiz.components.Questions
import com.example.triviaquiz.components.ShimmerAnimation
import com.example.triviaquiz.model.QuestionItem

@Composable
fun TriviaHome(viewModel: TriviaViewModel = hiltViewModel()) {

    val questionIndex = remember {
        mutableStateOf(0)
    }

    val questions = viewModel.data.value.data?.toMutableList()

    if (viewModel.data.value.loading == true) {

        LazyColumn(){
            repeat(5) {
                item {
                    ShimmerAnimation()
                }
            }
        }
    }
    else {
        val question = try {
            questions?.get(questionIndex.value)
        } catch(e: Exception) { }

        Questions(
            question = question!! as QuestionItem,
            viewModel = viewModel,
            questionIndex =  questionIndex){
                if (it < questions!!.size) questionIndex.value = questionIndex.value + 1
        }
    }

//    Questions(
//        viewModel = viewModel,
//        question = ,
//        questionIndex = index
//    )
}