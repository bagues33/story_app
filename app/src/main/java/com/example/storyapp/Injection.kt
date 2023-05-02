package com.example.storyapp

import android.app.Application
import android.content.Context
import com.example.storyapp.repository.StoryAppRepository

object Injection {
    fun provideRepository(app: Application, context: Context): StoryAppRepository {
        val apiService = ApiConfig.getApiService(context)
        return StoryAppRepository(apiService)
    }
}