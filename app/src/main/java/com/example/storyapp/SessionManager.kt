package com.example.storyapp

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private var prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val editor = prefs.edit()

    fun saveToken(prefKey: String, token: String) {
        editor.putString(prefKey, token)
        editor.apply()
    }

    fun logOut(context: Context) {
        editor.remove("token")
        editor.remove("status")
        editor.apply()
    }

    fun clearSession() {
        editor.clear().apply()
    }

    companion object {
        const val PREFS_NAME = "storyapp.pref"
        const val KEY_TOKEN = "key.token"
        const val KEY_EMAIL = "key.email"
        const val KEY_USERNAME = "key.username"
    }

    val getToken = prefs.getString(KEY_TOKEN, "")
    val getEmail = prefs.getString(KEY_EMAIL, "")
    val getUserName = prefs.getString(KEY_USERNAME, "")

}