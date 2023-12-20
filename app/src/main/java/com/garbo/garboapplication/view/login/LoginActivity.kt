package com.garbo.garboapplication.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

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
        playAnimation()
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
        with(binding) {
            loginButton.setOnClickListener {
                val email = edLoginEmail.text.toString()
                val pass = edLoginPassword.text.toString()

                when {
                    email.isBlank() -> {
                        edLoginEmail.requestFocus()
                        edLoginEmail.error = getString(R.string.error_empty_email)
                    }

                    pass.isBlank() -> {
                        edLoginPassword.requestFocus()
                        edLoginPassword.error = getString(R.string.error_empty_password)
                    }

                    else -> {
                        login(email, pass)
                    }
                }
            }

            signupButton.setOnClickListener {
                val intentToSignup = Intent(this@LoginActivity, SignupActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intentToSignup)
                finish()
            }
        }
    }

    private fun login(email: String, pass: String) {
        viewModel.login(email, pass).observe(this@LoginActivity) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        val data = result.data
                        val loginResult = data.loginResult!!
                        val user =
                            UserModel(
                                email,
                                loginResult.token,
                                loginResult.name,
                                true
                            )

                        viewModel.saveSession(user).apply {
                            binding.progressBar.visibility = View.GONE
                            AlertDialog.Builder(this@LoginActivity).apply {
                                setTitle("Selamat!")
                                setMessage(getString(R.string.successful_login))
                                setPositiveButton("Lanjut") { _, _ ->
                                    val intent =
                                        Intent(context, MainActivity::class.java)
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