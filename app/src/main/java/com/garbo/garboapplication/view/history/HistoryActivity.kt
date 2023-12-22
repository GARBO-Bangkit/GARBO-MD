package com.garbo.garboapplication.view.history

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.garbo.garboapplication.databinding.ActivityHistoryBinding
import com.garbo.garboapplication.view.HistoryViewModelFactory
import com.garbo.garboapplication.view.login.LoginActivity
import com.google.android.material.tabs.TabLayoutMediator

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding

    private val viewModel by viewModels<HistoryViewModel> {
        HistoryViewModelFactory.getInstance(this)
    }

    private var _token = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            _token = user.token

            val viewPager = binding.viewPager
            val tabs = binding.tabs

            val adapter = SectionsPagerAdapter(this, _token)
            viewPager.adapter = adapter

            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> "History"
                    1 -> "Category"
                    else -> null
                }
            }.attach()
        }
    }

    private fun setupView() {
        val toolbar = binding.appBar.toolbarCustom
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    companion object {
        const val TOKEN_KEY = "token_key"
    }
}