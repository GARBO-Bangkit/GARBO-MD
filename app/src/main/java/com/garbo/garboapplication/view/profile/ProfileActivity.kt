package com.garbo.garboapplication.view.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.garbo.garboapplication.R
import com.garbo.garboapplication.databinding.ActivityProfileBinding
import com.garbo.garboapplication.view.HomeViewModelFactory
import com.garbo.garboapplication.view.dashboard.HomeViewModel
import com.garbo.garboapplication.view.login.LoginActivity

class ProfileActivity : AppCompatActivity() {
    private val viewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            else{
                setupProfile(user.name, user.username, user.email)
            }
        }

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView(){
        val toolbar = binding.appBar.toolbarCustom
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.btnLogout.setOnClickListener { viewModel.logout() }
    }
    private fun setupProfile(name: String, username: String, email: String){
        with(binding){
            tvName.text = name
            tvUsername.text = username
            tvEmail.text = email
        }
    }
}