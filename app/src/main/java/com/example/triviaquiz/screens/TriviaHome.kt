package com.example.triviaquiz.screens

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getSystemService
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.triviaquiz.components.QuestionLayout
import com.example.triviaquiz.components.Questions
import com.example.triviaquiz.components.ShimmerAnimation
import com.example.triviaquiz.model.QuestionItem
import com.example.triviaquiz.util.CheckConnectivity

@Composable
fun TriviaHome(viewModel: TriviaViewModel = hiltViewModel(), context: Context) {

    val questionIndex = rememberSaveable {
        mutableStateOf(0)
    }

    if (viewModel.checkConnectivity(context)) {
        val questions = viewModel.data.value.data?.toMutableList()
        if (viewModel.data.value.loading == true) {
            LazyColumn() {
                repeat(5) {
                    item {
                        ShimmerAnimation()
                    }
                }
            }
        } else {
            val question = try {
                questions?.get(questionIndex.value)
            } catch (e: Exception) {
            }

            Questions(
                question = question!! as QuestionItem,
                viewModel = viewModel,
                questionIndex = questionIndex
            ) {
                if (it < questions!!.size) questionIndex.value = questionIndex.value + 1
            }
        }
    } else {
        NoInternetAvailable()
    }

//    Questions(
//        viewModel = viewModel,
//        question = ,
//        questionIndex = index
//    )
}