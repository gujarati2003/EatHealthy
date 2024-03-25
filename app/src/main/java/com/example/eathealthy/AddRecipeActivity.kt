package com.example.eathealthy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eathealthy.databinding.ActivityAddRecipeBinding

class AddRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddRecipeBinding
    private lateinit var userDbHelper: UserDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDbHelper = UserDbHelper(this)

    }
}