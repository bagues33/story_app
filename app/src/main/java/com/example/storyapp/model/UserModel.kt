package com.example.storyapp.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("token")
    val token: String
)
