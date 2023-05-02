package com.example.storyapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyapp.database.StoryApp
import com.example.storyapp.repository.StoryAppRepository


class MainViewModel(storyAppRepository: StoryAppRepository) : ViewModel() {

    val stories: LiveData<PagingData<StoryApp>> = storyAppRepository.getStories().cachedIn(viewModelScope)

}

