package com.example.storyapp.model

import com.example.storyapp.database.StoryApp
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("loginResult")
    val loginResult: LoginResult,
    @SerializedName("message")
    val message: String
)

data class LoginResult(
    @SerializedName("name")
    val name: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("userId")
    val userId: String
)

data class SignUpResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String
)

data class PostStoryResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String
)

data class StoriesResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("listStory")
    val listStory: List<StoryApp>,
    @SerializedName("message")
    val message: String
)
