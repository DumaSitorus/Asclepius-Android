package com.dicoding.asclepius.skinCencerPrediction.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.yalantis.ucrop.UCrop
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var imageClassifier: ImageClassifierHelper
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageClassifier = ImageClassifierHelper(this)

        binding.galleryButton.setOnClickListener {startGallery() }

        binding.analyzeButton.setOnClickListener {
            if (currentImageUri != null) {
                analyzeImage()
            } else {
                showToast("Please select an image first")
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun startGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }


    private fun showImage() {
        binding.previewImageView.setImageURI(currentImageUri)
    }

    private fun analyzeImage() {
        currentImageUri?.let { uri ->
            val result = imageClassifier.classifyStaticImage(uri)
            if (result != null) {
                val resultText = result.first
                val confidence = result.second
                moveToResult(resultText, confidence)
            } else {
                showToast("Failed to analyze image")
            }
        }
    }

    private fun moveToResult(isCancer: String, confidence: Float) {
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("IS_CANCER", isCancer)
            putExtra("CONFIDENCE", confidence)
            putExtra("IMAGE_URI", currentImageUri.toString())
        }
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            val selectedImageUri = data?.data
            if (selectedImageUri != null) {
                startCrop(selectedImageUri)
            }
        } else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            val resultUri = UCrop.getOutput(data!!)
            if (resultUri != null) {
                currentImageUri = resultUri
                showImage()
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(data!!)
            Log.e("Crop Error", cropError?.message ?: "Unknown error")
        }
    }

    private fun startCrop(sourceUri: Uri) {
        val destinationUri = Uri.fromFile(File(cacheDir, "${System.currentTimeMillis()}.jpg"))
        UCrop.of(sourceUri, destinationUri)
            .withMaxResultSize(1080, 1080)
            .start(this)
    }

    companion object {
        private const val GALLERY_REQUEST_CODE = 1001
    }

}