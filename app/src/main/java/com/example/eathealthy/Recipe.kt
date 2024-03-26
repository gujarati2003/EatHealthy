package com.example.eathealthy

data class Recipe(val id: Int, val userId: Int, val name: String, val img: ByteArray, val ingredients: String, val directions: String)
