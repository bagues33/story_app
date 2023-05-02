package com.example.storyapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "story_app")
data class StoryApp(
    @field:SerializedName("createdAt")
    val createdAt: String,
    @field:SerializedName("description")
    val description: String,
    @PrimaryKey
    @field:SerializedName("id")
    val id: String,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("photoUrl")
    val photoUrl: String
)
