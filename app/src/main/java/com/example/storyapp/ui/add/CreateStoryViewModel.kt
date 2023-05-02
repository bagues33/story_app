package com.example.storyapp.ui.add

import androidx.lifecycle.ViewModel
import com.example.storyapp.repository.StoryAppRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class CreateStoryViewModel(private val storyAppRepository: StoryAppRepository): ViewModel() {
    fun postStory(file: MultipartBody.Part, description: RequestBody) = storyAppRepository.postStory(file, description)
}