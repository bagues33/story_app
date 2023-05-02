package com.example.storyapp.register

import androidx.lifecycle.ViewModel
import com.example.storyapp.repository.StoryAppRepository

class RegisterViewModel(private val storyAppRepository: StoryAppRepository) : ViewModel() {
    fun signUp(name: String, email: String, password: String) = storyAppRepository.postSignUp(name, email, password)
}