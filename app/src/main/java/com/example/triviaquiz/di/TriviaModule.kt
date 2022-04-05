package com.example.triviaquiz.di

import com.example.triviaquiz.network.QuestionApi
import com.example.triviaquiz.repository.QuestionRepository
import com.example.triviaquiz.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TriviaModule {

    @Singleton
    @Provides
    fun provideQuestionRepository(api: QuestionApi) =
        QuestionRepository(api)


    @Singleton
    @Provides
    fun provideQuestionApi():QuestionApi{
        return Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuestionApi::class.java)
    }
}