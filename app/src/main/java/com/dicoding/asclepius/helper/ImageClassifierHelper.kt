package com.dicoding.asclepius.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.dicoding.asclepius.ml.CancerClassification
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import java.io.IOException

class ImageClassifierHelper(private val context: Context) {
    private lateinit var model: CancerClassification
    private fun setupImageClassifier() {
        try {
            model = CancerClassification.newInstance(context)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun classifyStaticImage(imageUri: Uri): Pair<String, Float>? {
        setupImageClassifier()
        try {
            val scaledBitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(context.contentResolver, imageUri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
            }?.copy(Bitmap.Config.ARGB_8888, true)?.let { bitmap ->
                Bitmap.createScaledBitmap(bitmap, 224, 224, true)
            }

            if (scaledBitmap != null) {
                val tensorImage = TensorImage(DataType.FLOAT32)
                tensorImage.load(scaledBitmap)

                val outputs = model.process(tensorImage)
                val outputBuffer = outputs.probabilityAsTensorBuffer
                val probabilities = outputBuffer.floatArray

                val confidenceCancerDetected = probabilities[1] * 100
                val confidenceNoCancer = probabilities[0] * 100

                val resultText = if (confidenceCancerDetected > confidenceNoCancer) {
                    "Cancer"
                } else {
                    "Non Cancer"
                }
                val confidence = maxOf(confidenceCancerDetected, confidenceNoCancer)

                return Pair(resultText, confidence)
            }

        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            model.close()
        }
        return null
    }
}
