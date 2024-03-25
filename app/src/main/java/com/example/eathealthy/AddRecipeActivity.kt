package com.example.eathealthy

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eathealthy.databinding.ActivityAddRecipeBinding
import java.io.ByteArrayOutputStream
import android.net.Uri

class AddRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddRecipeBinding
    private lateinit var userDbHelper: UserDbHelper
    private var username: String? = null
    private var password: String? = null
    private var id: Int? = null
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_IMAGE_FROM_GALLERY = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDbHelper = UserDbHelper(this)
        username = intent.getStringExtra("username")
        password = intent.getStringExtra("password")
        if (username != null && password != null) {
            id = userDbHelper.getId(username!!, password!!)
        }

        binding.uploadButton.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, REQUEST_IMAGE_FROM_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Handle image capture from camera
            // ...
        } else if (requestCode == REQUEST_IMAGE_FROM_GALLERY && resultCode == RESULT_OK) {
            // Handle image selection from gallery
            val selectedImageUri: Uri? = data?.data
            if (selectedImageUri != null) {
                val imageBitmap =
                    MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri)
                // Convert Bitmap to ByteArray
                val stream = ByteArrayOutputStream()
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val byteArray: ByteArray = stream.toByteArray()

                // Save image to the database along with other recipe information
                val recipeName = binding.enterName.text.toString()
                val ingredients = binding.enterIngredient1.text.toString()
                val directions = binding.enterDirections1.text.toString()
                val recipeId =
                    userDbHelper.addRecipe(id, recipeName, byteArray, ingredients, directions)
                if (recipeId != -1L) {
                    binding.photoImageView.setImageBitmap(imageBitmap)
                    Toast.makeText(this, "Recipe added successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to add recipe", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}