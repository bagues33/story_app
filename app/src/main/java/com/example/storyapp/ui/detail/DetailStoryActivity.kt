package com.example.storyapp.ui.detail

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.storyapp.R
import com.example.storyapp.database.StoryApp
import com.example.storyapp.databinding.ActivityDetailStoryBinding
import coil.imageLoader
import coil.request.ImageRequest

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        postponeEnterTransition()

        val story = StoryApp(
            createdAt = "",
            description = intent.getStringExtra("description") ?: "",
            id = intent.getStringExtra("id") ?: "",
            name = intent.getStringExtra("name") ?: "",
            lat = intent.getDoubleExtra("lat", 0.0),
            lon = intent.getDoubleExtra("lon", 0.0),
            photoUrl = intent.getStringExtra("photoUrl") ?: ""
        )

        binding.apply {
            ivDetailPhoto.setImageUrl(story.photoUrl, true)
            tvDetailName.text = story.name
            tvDetailDescription.text = story.description
        }

        val request = ImageRequest.Builder(this)
            .data(intent.getStringExtra("photoUrl"))
            .target(
                onSuccess = {
                    startPostponedEnterTransition()
                },
                onError = {
                    startPostponedEnterTransition()
                }
            )
            .build()

        applicationContext.imageLoader.enqueue(request)

        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.detail_story)
        }

        binding.executePendingBindings()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun ImageView.setImageUrl(url: String, isCenterCrop: Boolean) {
        Glide.with(context)
            .load(url)
            .centerCrop()
            .into(this)
    }

}

