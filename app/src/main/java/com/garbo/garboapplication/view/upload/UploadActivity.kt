package com.garbo.garboapplication.view.upload

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.garbo.garboapplication.R
import com.garbo.garboapplication.databinding.ActivityUploadBinding
import com.garbo.garboapplication.getImageUri
import com.garbo.garboapplication.view.UploadViewModelFactory

class UploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadBinding

    private var currentImageUri: Uri? = null

    private val viewModel by viewModels<UploadViewModel> {
        UploadViewModelFactory.getInstance(this)
    }

    private var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        viewModel.getSession().observe(this) { user ->
//            if (!user.isLogin) {
//                startActivity(Intent(this, LoginActivity::class.java))
//                finish()
//            }
//            token = user.token
//        }

        with(binding) {
            btnGallery.setOnClickListener { startGallery() }
            btnCamera.setOnClickListener { startCamera() }
            buttonAdd.setOnClickListener { uploadImage(token) }
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            AlertDialog.Builder(this).apply {
                setTitle("No Media")
                setMessage(getString(R.string.invalid_choose_image))
                setPositiveButton("Ok") { dialog, _ ->
                    dialog.dismiss()
                }
                create()
                show()
            }
        }
    }

    private fun startCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                PERMISSION_REQUEST_CAMERA
            )
        } else {
            currentImageUri = getImageUri(this)
            launcherIntentCamera.launch(currentImageUri)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CAMERA -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    currentImageUri = getImageUri(this)
                    launcherIntentCamera.launch(currentImageUri)
                } else {
                    AlertDialog.Builder(this).apply {
                        setTitle("Error")
                        setMessage(getString(R.string.permission_not_granted))
                        setPositiveButton("Ok") { dialog, _ ->
                            dialog.dismiss()
                        }
                        create()
                        show()
                    }
                }
                return
            }
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.ivPreview.setImageURI(it)
        }
    }

    private fun uploadImage(token: String) {
        finish()
//        currentImageUri?.let { uri ->
//            val imageFile = uriToFile(uri, this).reduceFileImage()
//
//            viewModel.uploadImage(token, imageFile).observe(this) { result ->
//                if (result != null) {
//                    when (result) {
//                        is Result.Loading -> {
//                            showLoading(true)
//                        }
//
//                        is Result.Success -> {
//                            showLoading(false)
//                            AlertDialog.Builder(this).apply {
//                                setTitle("Selamat!")
//                                setMessage(getString(R.string.successful_upload))
//                                setPositiveButton("Lanjut") { _, _ ->
//                                    val intent = Intent(context, HomeActivity::class.java)
//                                    intent.flags =
//                                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                                    startActivity(intent)
//                                    finish()
//                                }
//                                create()
//                                show()
//                            }
//                        }
//
//                        is Result.Error -> {
//                            AlertDialog.Builder(this).apply {
//                                setTitle("Error")
//                                setMessage("Terjadi kesalahan" + result.error)
//                                setPositiveButton("Ok") { dialog, _ ->
//                                    dialog.dismiss()
//                                }
//                                create()
//                                show()
//                            }
//                            showLoading(false)
//                        }
//                    }
//                }
//            }
//        } ?: AlertDialog.Builder(this).apply {
//            setTitle("Error")
//            setMessage(getString(R.string.empty_image_warning))
//            setPositiveButton("Ok") { dialog, _ ->
//                dialog.dismiss()
//            }
//            create()
//            show()
//        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val PERMISSION_REQUEST_CAMERA = 1
    }
}