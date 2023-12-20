package com.garbo.garboapplication.view.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.garbo.garboapplication.R
import com.garbo.garboapplication.databinding.ActivityHomeBinding
import com.garbo.garboapplication.view.history.HistoryActivity
import com.garbo.garboapplication.view.profile.ProfileActivity
import com.garbo.garboapplication.view.upload.UploadActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding.quickMenus){
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