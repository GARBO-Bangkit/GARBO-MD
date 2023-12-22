package com.garbo.garboapplication.view.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.garbo.garboapplication.Result
import com.garbo.garboapplication.databinding.ActivityHomeBinding
import com.garbo.garboapplication.getDateFromTimestamp
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

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                _token = user.token
                getPoints(_token)
            }
        }
    }

    private fun getPoints(token: String) {
        viewModel.getPoints(token).observe(this) { result ->
            Log.d("REQUEST", "point")
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val data = result.data
                        binding.pointsContainer.tvPointsValue.text = data.points
                        getLatestHistory(_token)
                    }

                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE

                        val statusCode = result.error.let { message ->
                            Regex("HTTP (\\d+)").find(message)?.groups?.get(1)?.value
                        }

                        when (statusCode) {
                            "401" -> {
                                val message = "Token has expired"
                                AlertDialog.Builder(this).apply {
                                    setTitle("Timeout!")
                                    setMessage(message)
                                    setPositiveButton("Lanjut") { _, _ ->
                                        val intent =
                                            Intent(context, LoginActivity::class.java)
                                        intent.flags =
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                        startActivity(intent)
                                        finish()
                                    }
                                    create()
                                    show()
                                }
                            }

                            else -> {
                                AlertDialog.Builder(this).apply {
                                    setTitle("Error")
                                    setMessage("Terjadi kesalahan\n" + result.error)
                                    setPositiveButton("Refresh") { _, _ ->
                                        getPoints(token)
                                    }
                                    setNegativeButton("Cancel") { dialog, _ ->
                                        dialog.dismiss()
                                    }
                                    create()
                                    show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getLatestHistory(token: String){
        viewModel.getLatestHistory(token).observe(this) { result ->
            Log.d("REQUEST", "history")
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val data = result.data
                        with(binding.itemRowLatestHistory){
                            Glide.with(this@HomeActivity).load(data.photo_url).into(ivItemPhoto)
                            tvItemName.text = data.classification
                            tvItemDate.text = data.timestamp?.let { getDateFromTimestamp(it) }
                            tvItemPoints.text = when (data.classification) {
                                in setOf("cardboard", "paper") -> "+10 points"
                                "plastic" -> "+15 points"
                                in setOf("glass", "metal") -> "+20 points"
                                else -> "+0 points"
                            }
                        }
                    }

                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE

                        val statusCode = result.error.let { message ->
                            Regex("HTTP (\\d+)").find(message)?.groups?.get(1)?.value
                        }

                        when (statusCode) {
                            "401" -> {
                                val message = "Token has expired"
                                AlertDialog.Builder(this).apply {
                                    setTitle("Timeout!")
                                    setMessage(message)
                                    setPositiveButton("Lanjut") { _, _ ->
                                        val intent =
                                            Intent(context, LoginActivity::class.java)
                                        intent.flags =
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                        startActivity(intent)
                                        finish()
                                    }
                                    create()
                                    show()
                                }
                            }

                            else -> {
                                AlertDialog.Builder(this).apply {
                                    setTitle("Error")
                                    setMessage("Terjadi kesalahan\n" + result.error)
                                    setPositiveButton("Refresh") { _, _ ->
                                        getPoints(token)
                                    }
                                    setNegativeButton("Cancel") { dialog, _ ->
                                        dialog.dismiss()
                                    }
                                    create()
                                    show()
                                }
                            }
                        }
                    }
                }
            }
        }
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
        binding.btnViewMore.setOnClickListener {
            val intentToHistory = Intent(this@HomeActivity, HistoryActivity::class.java)
            startActivity(intentToHistory)
        }
    }
}