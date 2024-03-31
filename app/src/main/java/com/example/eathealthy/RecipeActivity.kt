package com.example.eathealthy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eathealthy.databinding.ActivityRecipeBinding
import android.graphics.BitmapFactory
import android.widget.Toast


class RecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeBinding
    private lateinit var userDbHelper: UserDbHelper
    private var id: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userDbHelper = UserDbHelper(this)
        id = intent.getIntExtra("id", 0)

        val recipeList = userDbHelper.getRecipe(id!!)
        val recipe = recipeList[0]
        val creator = userDbHelper.getNameById(recipe.userId)


        binding.recipeName.text = recipe.name

        val bitmap = BitmapFactory.decodeByteArray(recipe.img, 0, recipe.img.size)
        binding.foodimg.setImageBitmap(bitmap)

        binding.madeBy.text = "By: $creator"
        binding.ingredientNum.text = recipe.ingredients
        binding.directionNum.text = recipe.directions

        binding.favsBtn.setOnClickListener{
            userDbHelper.addToFavorites(id!!, recipe.id)
            Toast.makeText(this, "${recipe.name} added to favorites", Toast.LENGTH_SHORT).show()
        }

    }
}