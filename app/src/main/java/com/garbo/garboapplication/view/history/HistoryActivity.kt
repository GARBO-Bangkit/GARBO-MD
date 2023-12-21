package com.garbo.garboapplication.view.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.garbo.garboapplication.R
import com.garbo.garboapplication.databinding.ActivityHistoryBinding
import com.google.android.material.tabs.TabLayoutMediator

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager = binding.viewPager
        val tabs = binding.tabs

        val adapter = SectionsPagerAdapter(this)
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