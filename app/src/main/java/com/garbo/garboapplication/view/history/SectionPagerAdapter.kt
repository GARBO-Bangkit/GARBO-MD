package com.garbo.garboapplication.view.history

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.garbo.garboapplication.data.response.HistoryResponseItem

class SectionsPagerAdapter(activity: AppCompatActivity, private val token: String) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HistoryFragment().apply {
                arguments = Bundle().apply { putString(HistoryActivity.TOKEN_KEY, token) }
            }
            1 -> CategoryFragment().apply {
                arguments = Bundle().apply { putString(HistoryActivity.TOKEN_KEY, token) }
            }
            else -> throw IllegalStateException("Invalid position $position")
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}