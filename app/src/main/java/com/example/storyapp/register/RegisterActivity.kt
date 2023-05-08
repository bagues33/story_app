package com.example.storyapp.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.R
import com.example.storyapp.repository.Result
import com.example.storyapp.ViewModelFactory
import com.example.storyapp.databinding.ActivityRegisterBinding
import com.example.storyapp.isEmailValid
import com.example.storyapp.login.LoginActivity
import com.example.storyapp.model.SignUpResponse
import com.example.storyapp.ui.setting.SettingPreferences
import com.example.storyapp.ui.setting.SettingViewModel
import com.example.storyapp.ui.setting.SettingViewModelFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels {
        ViewModelFactory(application, this)
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()
        getSetting()
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

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            when {
                name.isEmpty() -> {
                    binding.nameEditTextLayout.error = "Masukkan nama"
                }
                email.isEmpty() -> {
                    binding.emailEditText.error = "Masukkan email"
                }
                !email.isEmailValid() -> {
                    binding.emailEditText.error = getString(R.string.invalid_email)
                }
                password.isEmpty() -> {
                    binding.passwordEditText.error = "Masukkan password"
                }
                password.length < 8 -> {
                    binding.passwordEditText.error = getString(R.string.password_must_more8)
                }
                else -> {

                    registerViewModel.signUp(name, email, password).observe(this) {
                        if (it != null) {
                            when(it) {
                                is Result.Loading -> {
                                    showLoading(true)
                                }
                                is Result.Success -> {
                                    showLoading(false)
                                    registerProcess(it.data)
                                }
                                is Result.Error -> {
                                    showLoading(false)
                                    Toast.makeText(this, "Email is already taken", Toast.LENGTH_LONG).show()
                                }
                                else -> {}
                            }
                        }
                    }

                }
            }
        }

        binding.tvActionLogin.setOnClickListener {
            val i = Intent(this@RegisterActivity, LoginActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(i)
            finish()
        }
    }

    private fun registerProcess(data: SignUpResponse) {
        if (data.error) {
            Toast.makeText(this, "Gagal Sign Up", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Sign Up berhasil, silahkan login!", Toast.LENGTH_LONG).show()
            LoginActivity.start(this)
            finish()
        }
    }

    private fun showLoading(state: Boolean) {
        binding.loadingRegister.isVisible = state
        binding.emailEditText.isInvisible = state
        binding.nameEditTextLayout.isInvisible = state
        binding.passwordEditText.isInvisible = state
        binding.signupButton.isInvisible = state
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val nameTextView = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(500)
        val nameEditTextLayout = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.emailEditText, View.ALPHA, 1f).setDuration(500)
        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.passwordEditText, View.ALPHA, 1f).setDuration(500)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(500)


        AnimatorSet().apply {
            playSequentially(
                title,
                nameTextView,
                nameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                signup
            )
            startDelay = 500
        }.start()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}