package com.example.eathealthy

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eathealthy.databinding.ActivityCreateAccountBinding

class CreateAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateAccountBinding
    private lateinit var userDbHelper: UserDbHelper
    private lateinit var username: String
    private lateinit var password: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDbHelper = UserDbHelper(this)

        binding.createAccountBtn.setOnClickListener{
            addUser()
        }
    }

    private fun addUser(){
        val firstName = binding.enterName1.text.toString()
        val lastName = binding.enterName2.text.toString()
        val email = binding.enterUsername.text.toString()
        val password1 = binding.enterPassword.text.toString()
        username = binding.enterUsername.text.trim().toString()
        password = binding.enterPassword.text.trim().toString()

        if(firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank() && isValidEmail(email)){
            if (!isEmailExists(email)) {
                val success = userDbHelper.addUser(firstName, lastName, email, password1)
                if (success != -1L) {
                    Toast.makeText(this, "Welcome, $firstName!", Toast.LENGTH_SHORT).show()
                    val i = Intent(this, HomeActivity::class.java)
                    i.putExtra("username", username)
                    i.putExtra("password", password)
                    Toast.makeText(this, "$email $password1", Toast.LENGTH_LONG).show()
                    startActivity(i)
                } else {
                    Toast.makeText(this, "Failed to create account", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Email already exists, please use a different one", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please enter valid information", Toast.LENGTH_SHORT).show()
        }
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


