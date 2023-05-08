package com.example.storyapp

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.login.LoginViewModel
import com.example.storyapp.register.RegisterViewModel
import com.example.storyapp.ui.add.CreateStoryViewModel
import com.example.storyapp.ui.main.MainViewModel
import com.example.storyapp.ui.maps.MapsViewModel

class ViewModelFactory(private val app: Application, private val context: Context) : ViewModelProvider.Factory {

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application, context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application, context)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(Injection.provideRepository(app, context)) as T
        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(Injection.provideRepository(app, context)) as T
        } else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(Injection.provideRepository(app, context)) as T
        } else if (modelClass.isAssignableFrom(CreateStoryViewModel::class.java)) {
            return CreateStoryViewModel(Injection.provideRepository(app, context)) as T
        } else if (modelClass.isAssignableFrom(MapsViewModel::class.java)) {
            return MapsViewModel(Injection.provideRepository(app, context)) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")

    }

}