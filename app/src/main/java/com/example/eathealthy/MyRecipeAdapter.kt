package com.example.eathealthy

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MyRecipeAdapter(private val username: String, private val password: String, private val context: Context, private val recipeList: List<Recipe>, private val id: Int, private val type: String) : RecyclerView.Adapter<MyRecipeAdapter.RecipeViewHolder>() {
    private lateinit var userDbHelper: UserDbHelper
    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipeName: TextView = view.findViewById(R.id.recipeName)
        val recipeIngredients: TextView = view.findViewById(R.id.recipeIngredients)
        val recipeImg: ImageView = view.findViewById(R.id.recipeImage)
        val recipeCreater: TextView = view.findViewById(R.id.recipeCreatorBy)
        val removeBtn: Button = view.findViewById(R.id.removeBtn)
        val directions: TextView = view.findViewById(R.id.recipeDirections)
        val updateBtn: Button = view.findViewById(R.id.updateBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_recipe_item, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        userDbHelper = UserDbHelper(context)
        val recipe = recipeList[position]
        holder.recipeName.text = recipe.name
        val creator = userDbHelper.getNameById(recipe.userId)
        holder.recipeIngredients.text = recipe.ingredients
        holder.recipeCreater.text = "By: $creator"
        holder.directions.text = recipe.directions

        val bitmap = BitmapFactory.decodeByteArray(recipe.img, 0, recipe.img.size)
        holder.recipeImg.setImageBitmap(bitmap)

        holder.removeBtn.setOnClickListener {
            userDbHelper.removeRecipe(recipe.id)
            Toast.makeText(context, "Recipe Removed", Toast.LENGTH_SHORT).show()
            val i = Intent(context, MyRecipeActivity::class.java)
            i.putExtra("id", recipe.id)
            context.startActivity(i)
        }

        holder.updateBtn.setOnClickListener {
            val i = Intent(context, UpdateActivity::class.java)
            i.putExtra("id", recipe.id)
            context.startActivity(i)
        }

    }

    override fun getItemCount() = recipeList.size
}