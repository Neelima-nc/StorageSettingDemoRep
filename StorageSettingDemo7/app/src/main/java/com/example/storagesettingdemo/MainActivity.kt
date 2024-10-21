package com.example.storagesettingdemo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView: ImageView = findViewById(R.id.imageView)
        val buttonSaveImage: Button = findViewById(R.id.buttonSaveImage)
        val buttonLoadImage: Button = findViewById(R.id.buttonLoadImage)
        val buttonSaveSettings: Button = findViewById(R.id.buttonSaveSettings)
        val buttonLoadSettings: Button = findViewById(R.id.buttonLoadSettings)

        // Save image to internal storage
        buttonSaveImage.setOnClickListener {
            saveImageToInternalStorage()
            Toast.makeText(this, "Image saved to internal storage", Toast.LENGTH_SHORT).show()
        }

        // Load image from internal storage
        buttonLoadImage.setOnClickListener {
            val bitmap = loadImageFromInternalStorage()
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap)
                Toast.makeText(this, "Loaded from internal storage", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No image found", Toast.LENGTH_SHORT).show()
            }
        }

        // Save user settings (e.g., enable dark mode)
        buttonSaveSettings.setOnClickListener {
            saveUserSettings("dark_mode", true)
            Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show()
        }

        // Load user settings (e.g., check if dark mode is enabled)
        buttonLoadSettings.setOnClickListener {
            val isDarkModeEnabled = loadUserSettings("dark_mode")
            Toast.makeText(this, "Dark Mode: $isDarkModeEnabled", Toast.LENGTH_SHORT).show()
        }
    }

    // Save an image to internal storage
    private fun saveImageToInternalStorage() {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.sample_image)
        val filename = "saved_image.png"
        val fos: FileOutputStream = openFileOutput(filename, Context.MODE_PRIVATE)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        fos.close()
    }

    // Load the saved image from internal storage
    private fun loadImageFromInternalStorage(): Bitmap? {
        return try {
            val fileInputStream = openFileInput("saved_image.png")
            BitmapFactory.decodeStream(fileInputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    // Save user settings using SharedPreferences
    private fun saveUserSettings(key: String, value: Boolean) {
        val sharedPreferences = getSharedPreferences("UserSettings", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    // Load user settings from SharedPreferences
    private fun loadUserSettings(key: String): Boolean {
        val sharedPreferences = getSharedPreferences("UserSettings", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(key, false) // Default to false if not found
    }
}
