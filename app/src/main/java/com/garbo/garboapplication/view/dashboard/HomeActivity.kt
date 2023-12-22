package com.garbo.garboapplication.view.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.garbo.garboapplication.databinding.ActivityHomeBinding
import com.garbo.garboapplication.view.HomeViewModelFactory
import com.garbo.garboapplication.view.history.HistoryActivity
import com.garbo.garboapplication.view.login.LoginActivity
import com.garbo.garboapplication.view.profile.ProfileActivity
import com.garbo.garboapplication.view.upload.UploadActivity

class HomeActivity : AppCompatActivity() {
    private val viewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityHomeBinding

    private var _token = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                _token = user.token
            }
        }

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        with(binding.quickMenus) {
            ivUploadMenu.setOnClickListener {
                val intentToUpload = Intent(this@HomeActivity, UploadActivity::class.java)
                startActivity(intentToUpload)
            }
            ivHistoryMenu.setOnClickListener {
                val intentToHistory = Intent(this@HomeActivity, HistoryActivity::class.java)
                startActivity(intentToHistory)
            }
            ivProfileMenu.setOnClickListener {
                val intentToProfile = Intent(this@HomeActivity, ProfileActivity::class.java)
                startActivity(intentToProfile)
            }
        }
    }
}