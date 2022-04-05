package com.example.triviaquiz.repository

import com.example.triviaquiz.data.DataOrException
import com.example.triviaquiz.model.QuestionItem
import com.example.triviaquiz.network.QuestionApi
import javax.inject.Inject


class QuestionRepository @Inject constructor(
    private val api: QuestionApi
    ) {
    private val dataOrException = DataOrException<
            ArrayList<QuestionItem>,
            Boolean,
            Exception>()

    suspend fun getAllQuestion(): DataOrException<ArrayList<QuestionItem>, Boolean, Exception> {
        try{
            dataOrException.loading = false
            dataOrException.data =  api.getAllQuestions()
            if(dataOrException.data.toString().isNotEmpty()) dataOrException.loading = false
        }catch (exception: Exception){
            dataOrException.exception = exception
        }

        return dataOrException
    }


}