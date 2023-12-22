package com.garbo.garboapplication.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.garbo.garboapplication.R
import com.garbo.garboapplication.Result
import com.garbo.garboapplication.data.pref.UserModel
import com.garbo.garboapplication.databinding.ActivityLoginBinding
import com.garbo.garboapplication.view.UserViewModelFactory
import com.garbo.garboapplication.view.dashboard.HomeActivity
import com.garbo.garboapplication.view.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        UserViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
    }

    private fun setupView() {
        window.insetsController?.hide(WindowInsets.Type.statusBars())
        supportActionBar?.hide()
    }

    private fun setupAction() {
        with(binding) {
            loginButton.setOnClickListener {
                val username = edLoginUsername.text.toString()
                val pass = edLoginPassword.text.toString()

                when {
                    username.isBlank() -> {
                        edLoginUsername.requestFocus()
                        edLoginUsername.error = getString(R.string.error_empty_username)
                    }

                    pass.isBlank() -> {
                        edLoginPassword.requestFocus()
                        edLoginPassword.error = getString(R.string.error_empty_password)
                    }

                    else -> {
                        login(username, pass)
                    }
                }
            }

            signupButton.setOnClickListener {
                val intentToSignup = Intent(this@LoginActivity, RegisterActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intentToSignup)
                finish()
            }
        }
    }

    private fun login(username: String, pass: String) {
        viewModel.login(username, pass).observe(this@LoginActivity) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        val data = result.data
                        val user =
                            UserModel(
                                data.email!!,
                                data.name!!,
                                username,
                                data.accessToken!!,
                                true
                            )

                        viewModel.saveSession(user).apply {
                            binding.progressBar.visibility = View.GONE
                            AlertDialog.Builder(this@LoginActivity).apply {
                                setTitle("Selamat!")
                                setMessage(getString(R.string.successful_login))
                                setPositiveButton("Lanjut") { _, _ ->
                                    val intent =
                                        Intent(context, HomeActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                    finish()
                                }
                                create()
                                show()
                            }
                        }
                    }

                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE

                        AlertDialog.Builder(this@LoginActivity).apply {
                            setTitle("Error")
                            setMessage("Login gagal.\nTerjadi kesalahan " + result.error)
                            setPositiveButton("Ok") { dialog, _ ->
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