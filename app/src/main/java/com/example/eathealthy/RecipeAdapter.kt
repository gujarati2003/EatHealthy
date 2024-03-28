package com.example.eathealthy
import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecipeAdapter(private val username: String, private val password: String, private val context: Context, private val recipeList: List<Recipe>, private val id: Int) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {
    private lateinit var userDbHelper: UserDbHelper
    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipeName: TextView = view.findViewById(R.id.recipeName)
        val recipeIngredients: TextView = view.findViewById(R.id.recipeIngredients)
        val recipeImg: ImageView = view.findViewById(R.id.recipeImage)
        val recipeCreater: TextView = view.findViewById(R.id.recipeCreator)
        val favBtn: Button = view.findViewById(R.id.favBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_item, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        userDbHelper = UserDbHelper(context)
        val recipe = recipeList[position]
        holder.recipeName.text = recipe.name
        val creator = userDbHelper.getNameById(recipe.userId)
        holder.recipeIngredients.text = recipe.ingredients
        holder.recipeCreater.text = creator

        val bitmap = BitmapFactory.decodeByteArray(recipe.img, 0, recipe.img.size)
        holder.recipeImg.setImageBitmap(bitmap)

        holder.favBtn.setOnClickListener {
            val id = userDbHelper.getId(username, password)
            userDbHelper.addToFavorites(id!!, recipe.id)

            Toast.makeText(holder.itemView.context, "${recipe.name} added to favorites", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = recipeList.size
}