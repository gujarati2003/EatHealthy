package com.example.eathealthy

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.eathealthy.databinding.ActivityAccountBinding

class AccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccountBinding
    private lateinit var userDbHelper: UserDbHelper
    private var username: String? = null
    private var password: String? = null
    private var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDbHelper = UserDbHelper(this)
        username = intent.getStringExtra("username")
        password = intent.getStringExtra("password")
        id = intent.getIntExtra("id", 0)

        if (username != null && password != null) {
            val cursor = userDbHelper.getUsersByEmailAndPassword(username!!, password!!)
            val name = userDbHelper.getName(username!!, password!!)
            binding.hi.text = "Hi $name"
        }

        binding.favoritesBtn.setOnClickListener{
            val i = Intent(this, FavoritesActivity::class.java)
            i.putExtra("username", username)
            i.putExtra("password", password)
            i.putExtra("id", id)
            startActivity(i)
        }

        binding.myRecipesBtn.setOnClickListener{
            val i = Intent(this, MyRecipeActivity::class.java)
            i.putExtra("username", username)
            i.putExtra("password", password)
            i.putExtra("id", id)
            startActivity(i)
        }

        binding.logOutbtn.setOnClickListener{
            val i = Intent(this, LoginOrCreateActivity::class.java)
            startActivity(i)
        }
    }
}
