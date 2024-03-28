package com.example.eathealthy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.eathealthy.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userDbHelper: UserDbHelper
    private lateinit var username: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userDbHelper = UserDbHelper(this)

        binding.loginBtn.setOnClickListener {
            username = binding.enterUsername.text.trim().toString()
            password = binding.enterPassword.text.trim().toString()
            if (login(username, password)) {
                val i = Intent(this, HomeActivity::class.java)
                i.putExtra("username", username)
                i.putExtra("password", password)

                Toast.makeText(this, "$username $password", Toast.LENGTH_LONG).show()
                startActivity(i)
            }
        }
    }

    private fun login(username: String, password: String): Boolean {
        if (username.isNotBlank() && password.isNotBlank() && isValidEmail(username)) {
            if (isEmailExists(username)) {
                val cursor = userDbHelper.getUsersByEmailAndPassword(username, password)
                if (cursor != null && cursor.moveToFirst()) {
                    val name = userDbHelper.getName(username, password)
                    Toast.makeText(this, "Welcome! $name", Toast.LENGTH_SHORT).show()
                    return true
                } else {
                    Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
                cursor?.close()
            } else {
                Toast.makeText(this, "Email not registered", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Invalid email or password format", Toast.LENGTH_SHORT).show()
        }
        return false
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isEmailExists(email: String): Boolean {
        val cursor = userDbHelper.getUserByEmail(email)
        val count = cursor?.count ?: 0
        cursor?.close()
        return count > 0
    }
}

