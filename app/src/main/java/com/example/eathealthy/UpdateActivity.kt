package com.example.eathealthy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eathealthy.databinding.ActivityUpdateBinding
import android.content.Intent
import android.graphics.BitmapFactory
import android.widget.Toast
import com.example.eathealthy.UserDbHelper


class UpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding
    private lateinit var userDbHelper: UserDbHelper
    private var id: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userDbHelper = UserDbHelper(this)
        id = intent.getIntExtra("id", 0)

        val recipeList = userDbHelper.getRecipe(id!!)
        val recipe = recipeList[0]
        val creator = userDbHelper.getNameById(recipe.userId)


        binding.recipeName.setText(recipe.name)

        val bitmap = BitmapFactory.decodeByteArray(recipe.img, 0, recipe.img.size)
        binding.foodimg.setImageBitmap(bitmap)

        binding.madeBy.text = "By: $creator"

        binding.ingredientNum.setText(recipe.ingredients)
        binding.directionNum.setText(recipe.directions)

        binding.updateBtn.setOnClickListener{
            userDbHelper.update(id!!, binding.recipeName.text.toString(), binding.ingredientNum.text.toString(), binding.directionNum.text.toString())
            val username = userDbHelper.getUsername(id!!)
            val password = userDbHelper.getPassword(id!!)
            Toast.makeText(this, "Recipe Updated", Toast.LENGTH_SHORT).show()
            val i = Intent(this, MyRecipeActivity::class.java)
            i.putExtra("id", recipe.id)
            i.putExtra("username", username)
            i.putExtra("password", password)
            startActivity(i)
        }

    }
}