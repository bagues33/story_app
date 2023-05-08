package com.example.storyapp.utils

import com.example.storyapp.database.StoryApp
import com.example.storyapp.model.StoryResponse

object DataDummy {
    fun generateDummyStory(): StoryResponse {
        val listStory = ArrayList<StoryApp>()
        for (i in 1..20) {
            val story = StoryApp(
                createdAt = "2023-02-09 06:58:13",
                description = "Description $i",
                id = "id_$i",
                lat = i.toDouble() * 10,
                lon = i.toDouble() * 10,
                name = "Name $i",
                photoUrl = "https://source.unsplash.com/gySMaocSdqs"
            )
            listStory.add(story)
        }

        return StoryResponse(
            error = false,
            message = "Stories fetched successfully",
            listStory = listStory
        )
    }
}