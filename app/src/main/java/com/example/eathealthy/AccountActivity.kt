package com.example.eathealthy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.eathealthy.databinding.ActivityAccountBinding

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
