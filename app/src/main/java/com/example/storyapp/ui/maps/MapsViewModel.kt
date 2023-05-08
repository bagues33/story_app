package com.example.storyapp.ui.maps

import androidx.lifecycle.ViewModel
import com.example.storyapp.repository.StoryAppRepository

class MapsViewModel(private val storyAppRepository: StoryAppRepository): ViewModel() {
    fun getStoriesWithLocation() = storyAppRepository.getStoryLocation()
}