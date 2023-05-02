package com.example.storyapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.storyapp.login.LoginActivity
import com.example.storyapp.ui.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var pref: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (getSupportActionBar() != null) {
            getSupportActionBar()!!.hide();
        }

        pref = SessionManager(this)
        val isLogin = pref.getToken
        Handler(Looper.getMainLooper()).postDelayed({
              if (isLogin != "") {
                  MainActivity.start(this)
                  finish()
              } else {
                  LoginActivity.start(this)
                  finish()
              }

        }, UiConstValue.LOADING_TIME)
    }

    object UiConstValue {
        const val LOADING_TIME = 1500L
    }
}