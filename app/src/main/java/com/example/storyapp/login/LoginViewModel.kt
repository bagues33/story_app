package com.example.storyapp.login

import androidx.lifecycle.ViewModel
import com.example.storyapp.repository.StoryAppRepository

class LoginViewModel(private val storyAppRepository: StoryAppRepository) : ViewModel() {
    fun login(email: String, password: String) = storyAppRepository.postLogin(email, password)
}