package com.example.eathealthy

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.eathealthy.databinding.ActivityAccountBinding
import com.example.eathealthy.databinding.ActivityLoginBinding

class AccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccountBinding
    private lateinit var userDbHelper: UserDbHelper
    private var username: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDbHelper = UserDbHelper(this)
        username = intent.getStringExtra("username")
        password = intent.getStringExtra("password")

        if (username != null && password != null) {
            val cursor = userDbHelper.getUsersByEmailAndPassword(username!!, password!!)
            val name = userDbHelper.getName(username!!, password!!)
            binding.hi.text = "Hi $name"
        }
    }
}
