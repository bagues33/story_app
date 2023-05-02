package com.example.storyapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.storyapp.ApiService
import com.example.storyapp.database.StoryApp
import com.example.storyapp.model.LoginResponse
import com.example.storyapp.model.PostStoryResponse
import com.example.storyapp.model.SignUpResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryAppRepository(private val apiService: ApiService) {

    fun getStories(): LiveData<PagingData<StoryApp>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService)
            }
        ).liveData
    }

    fun postStory(file: MultipartBody.Part, description: RequestBody): LiveData<Result<PostStoryResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.postStory(file, description)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e("CreateStoryViewModel", "postStory: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun postSignUp(name: String, email: String, password: String): LiveData<Result<SignUpResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.postSignUp(name, email, password)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e("RegisterViewModel", "postSignUp: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun postLogin(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.postLogin(email, password)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e("LoginViewModel", "postLogin: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }
}