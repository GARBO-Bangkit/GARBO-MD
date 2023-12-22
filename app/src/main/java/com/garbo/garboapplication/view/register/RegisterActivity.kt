package com.garbo.garboapplication.view.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.garbo.garboapplication.R
import com.garbo.garboapplication.Result
import com.garbo.garboapplication.databinding.ActivityRegisterBinding
import com.garbo.garboapplication.view.UserViewModelFactory
import com.garbo.garboapplication.view.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private val viewModel by viewModels<RegisterViewModel> {
        UserViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
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
            signupButton.setOnClickListener {
                val username = edRegisterUsername.text.toString()
                val name = edRegisterName.text.toString()
                val email = edRegisterEmail.text.toString()
                val pass = edRegisterPassword.text.toString()

                when {
                    username.isBlank() -> {
                        edRegisterUsername.requestFocus()
                        edRegisterUsername.error = getString(R.string.error_empty_username)
                    }

                    name.isBlank() -> {
                        edRegisterName.requestFocus()
                        edRegisterName.error = getString(R.string.error_empty_name)
                    }

                    email.isBlank() -> {
                        edRegisterEmail.requestFocus()
                        edRegisterEmail.error = getString(R.string.error_empty_email)
                    }

                    pass.isBlank() -> {
                        edRegisterPassword.requestFocus()
                        edRegisterPassword.error = getString(R.string.error_empty_password)
                    }

                    else -> {
                        viewModel.register(username, pass, name, email)
                            .observe(this@RegisterActivity) { result ->
                                if (result != null) {
                                    when (result) {
                                        is Result.Loading -> {
                                            binding.progressBar.visibility = View.VISIBLE
                                        }

                                        is Result.Success -> {
                                            binding.progressBar.visibility = View.GONE

                                            AlertDialog.Builder(this@RegisterActivity).apply {
                                                setTitle("Selamat!")
                                                setMessage(getString(R.string.successful_register))
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

                                        is Result.Error -> {
                                            binding.progressBar.visibility = View.GONE

                                            AlertDialog.Builder(this@RegisterActivity).apply {
                                                setTitle("Error")
                                                setMessage("Register gagal.\n" + result.error)
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
            }

            loginButton.setOnClickListener {
                val intentToLogin = Intent(this@RegisterActivity, LoginActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intentToLogin)
                finish()
            }
        }
    }
}