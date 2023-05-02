package com.example.storyapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [StoryApp::class], version = 1)
abstract class StoryAppDatabase : RoomDatabase() {
    abstract fun StoryAppDao(): StoryAppDao
    companion object {
        @Volatile
        private var INSTANCE: StoryAppDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): StoryAppDatabase {
            if (INSTANCE == null) {
                synchronized(StoryAppDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        StoryAppDatabase::class.java, "story_database")
                        .build()
                }
            }
            return INSTANCE as StoryAppDatabase
        }
    }
}