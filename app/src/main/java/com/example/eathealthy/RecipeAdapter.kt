package com.example.eathealthy
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eathealthy.R
import com.example.eathealthy.Recipe
import com.example.eathealthy.databinding.RecipeItemBinding

class RecipeAdapter(private val recipeList: List<Recipe>) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {
    private lateinit var binding: RecipeItemBinding
    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipeName: TextView = view.findViewById(R.id.recipeName)
        val recipeIngredients: TextView = view.findViewById(R.id.recipeIngredients)
        val recipeImg: ImageView = view.findViewById(R.id.recipeImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_item, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipeList[position]
        holder.recipeName.text = recipe.name
        holder.recipeIngredients.text = recipe.ingredients

        // Convert ByteArray to Bitmap
        val bitmap = BitmapFactory.decodeByteArray(recipe.img, 0, recipe.img.size)
        holder.recipeImg.setImageBitmap(bitmap)
    }

    override fun getItemCount() = recipeList.size
}