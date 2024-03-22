package com.example.eathealthy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eathealthy.databinding.ActivityLoginOrCreateBinding

class LoginOrCreateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginOrCreateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginOrCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener{
            val i = Intent(this, LoginActivity:: class .java)
            startActivity(i)
        }
        binding.createAccountBtn.setOnClickListener{
            val i = Intent(this, CreateAccountActivity:: class .java)
            startActivity(i)
        }
    }
}