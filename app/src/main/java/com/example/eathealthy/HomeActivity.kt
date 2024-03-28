package com.example.eathealthy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eathealthy.databinding.ActivityHomeBinding
import android.widget.Toast
import android.widget.Toast.*
import androidx.recyclerview.widget.LinearLayoutManager

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var userDbHelper: UserDbHelper
    private var username: String? = null
    private var password: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userDbHelper = UserDbHelper(this)
        username = intent.getStringExtra("username")
        password = intent.getStringExtra("password")

        val recipes = userDbHelper.getAllRecipes()
        val id = userDbHelper.getId(username!!, password!!)
        Toast.makeText(this, "$id", LENGTH_LONG).show()
        val adapter = RecipeAdapter(username!!, password!!,this, recipes, id!!)
        binding.recipesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.recipesRecyclerView.adapter = adapter

        binding.addBtn.setOnClickListener {
            val i = Intent(this, AddRecipeActivity::class.java)
            i.putExtra("username", username)
            i.putExtra("password", password)
            startActivity(i)
        }

        binding.accountBtn.setOnClickListener {
            val i = Intent(this, AccountActivity::class.java)
            i.putExtra("username", username)
            i.putExtra("password", password)
            startActivity(i)
        }
    }
}
