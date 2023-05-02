package com.example.storyapp.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.transition.TransitionInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.*
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.storyapp.adapter.StoryAdapter
import com.example.storyapp.customview.EmailEditText
import com.example.storyapp.databinding.ActivityMainBinding
import com.example.storyapp.ui.detail.DetailStoryActivity
import com.example.storyapp.ui.profile.ProfileActivity
import com.example.storyapp.ui.setting.SettingActivity
import com.example.storyapp.ui.setting.SettingPreferences
import com.example.storyapp.ui.setting.SettingViewModel
import com.example.storyapp.ui.setting.SettingViewModelFactory
import com.example.storyapp.ui.add.AddStoryActivity


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory(application, this)
    }

    private lateinit var pref: SessionManager
    private var token: String? = null

    private lateinit var adapter: StoryAdapter

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

//    private lateinit var emailEditText: EmailEditText
//    private lateinit var loginButton: Button

//    private lateinit Button loginButton;

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setContentView(R.layout.activity_setting)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvStory.addItemDecoration(itemDecoration)
        pref = SessionManager(this)
        token = pref.getToken

        val sharedElementReturnTransition = TransitionInflater.from(this).inflateTransition(android.R.transition.move)
        window.sharedElementReturnTransition = sharedElementReturnTransition

        mainViewModel.stories.observe(this, { data ->
            if (data != null) {
                adapter.submitData(lifecycle, data)
            }
        })

        binding.fabNewStory.setOnClickListener {
            AddStoryActivity.start(this)
        }

        getSetting()
        setupAdapter()

//        emailEditText = findViewById(R.id.emailEditText)
//        loginButton = findViewById(R.id.loginButton)

//        val loginButton = findViewById<Button>(R.id.loginButton)
//        val emailEditText = findViewById<EmailEditText>(R.id.emailEditText)


    }

private fun setupAdapter() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvStory.layoutManager = layoutManager

        adapter = StoryAdapter { story, imageView, nameView ->
            val intent = Intent(this, DetailStoryActivity::class.java)
            intent.putExtra("id", story.id)
            intent.putExtra("name", story.name)
            intent.putExtra("description", story.description)
            intent.putExtra("photoUrl", story.photoUrl)

            startActivity(intent)
        }

        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        binding.rvStory.viewTreeObserver
            .addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
    }

    private fun getSetting() {
        val pref = SettingPreferences.getInstance(dataStore)
        val mainViewModel =
            ViewModelProvider(this, SettingViewModelFactory(pref))[SettingViewModel::class.java]
        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_profile -> {
                val i = Intent(this, ProfileActivity::class.java)
                startActivity(i)
                return true
            }
            R.id.btn_setting -> {
                val setting = Intent(this, SettingActivity::class.java)
                startActivity(setting)
                return true
            }
            else -> return true
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }



}

