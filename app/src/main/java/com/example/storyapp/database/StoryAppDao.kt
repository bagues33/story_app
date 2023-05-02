package com.example.storyapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StoryAppDao {

    @Query("SELECT * FROM story_app")
    fun getAllStories(): List<StoryApp>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertStories(storyList: List<StoryApp>)

//    @Query("DELETE * FROM story_app")
//    suspend fun deleteAllStories()

}