package com.example.eathealthy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eathealthy.databinding.ActivityMyRecipeBinding
import androidx.recyclerview.widget.LinearLayoutManager


class MyRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyRecipeBinding
    private lateinit var userDbHelper: UserDbHelper
    private var username: String? = null
    private var password: String? = null
    private var id: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userDbHelper = UserDbHelper(this)
        username = intent.getStringExtra("username")
        password = intent.getStringExtra("password")
        id = intent.getIntExtra("id", 0)

        val recipes = userDbHelper.getMyRecipes(id!!)

        val adapter =MyRecipeAdapter(username!!, password!!,this, recipes, id!!, "MyRecipe")
        binding.recipesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.recipesRecyclerView.adapter = adapter
    }
}