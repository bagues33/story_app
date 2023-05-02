package com.example.storyapp.ui.profile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.storyapp.R
import com.example.storyapp.R.string
import com.example.storyapp.SessionManager
import com.example.storyapp.databinding.ActivityProfileBinding
import com.example.storyapp.login.LoginActivity

class ProfileActivity : AppCompatActivity() {
    private var _binding: ActivityProfileBinding? = null
    private val binding get() = _binding
    private lateinit var pref: SessionManager

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, ProfileActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        pref = SessionManager(this)

        Glide.with(this)
            .load(R.drawable.messi)
            .transform(RoundedCorners(16))
            .into(binding!!.ivProfile)

        setToolbar()
        setAction()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setToolbar() {
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.profile)
        }

        binding!!.tvUserName.text = pref.getUserName
        binding!!.tvEmail.text = pref.getEmail

    }

    private fun setAction() {
        binding!!.btnLogout.setOnClickListener {
            openLogoutDialog()
        }
    }

    private fun openLogoutDialog() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(getString(string.confirm_logout))
            ?.setPositiveButton(getString(string.logout)) { _, _ ->
                pref.clearSession()
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
            ?.setNegativeButton(getString(string.cancel), null)
        val alert = alertDialog.create()
        alert.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}