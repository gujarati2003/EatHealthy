package com.example.eathealthy

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UserDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "UserDatabase.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME_USER = "users"
        const val COLUMN_ID_USER = "id"
        const val COLUMN_FIRST_NAME_USER = "first_name"
        const val COLUMN_LAST_NAME_USER = "last_name"
        const val COLUMN_EMAIL_USER = "email"
        const val COLUMN_PASSWORD_USER = "password"
        const val TABLE_NAME_RECIPES = "recipes"
        const val COLUMN_ID_RECIPES = "id"
        const val COLUMN_ID_USER_RECIPES = "user_id" // Changed the name for clarity
        const val COLUMN_NAME_RECIPES = "name"
        const val COLUMN_IMG_RECIPES = "img"
        const val COLUMN_INGREDIENTS_RECIPES = "ingredients"
        const val COLUMN_DIRECTIONS_RECIPES = "directions"
        const val TABLE_NAME_FAVORITES = "favorites"
        const val COLUMN_ID_FAVORITES = "id"
        const val COLUMN_ID_USER_FAVORITES = "user_id" // Changed the name for clarity
        const val COLUMN_ID_RECIPES_FAVORITES = "recipe_id" // Changed the name for clarity
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_USERS = ("CREATE TABLE $TABLE_NAME_USER (" +
                "$COLUMN_ID_USER INTEGER PRIMARY KEY," +
                "$COLUMN_FIRST_NAME_USER TEXT," +
                "$COLUMN_LAST_NAME_USER TEXT," +
                "$COLUMN_EMAIL_USER TEXT," +
                "$COLUMN_PASSWORD_USER TEXT)")
        db?.execSQL(CREATE_TABLE_USERS)

        val CREATE_TABLE_RECIPES = ("CREATE TABLE $TABLE_NAME_RECIPES (" +
                "$COLUMN_ID_RECIPES INTEGER PRIMARY KEY," +
                "$COLUMN_ID_USER_RECIPES INTEGER," + // Added missing comma
                "$COLUMN_NAME_RECIPES TEXT," +
                "$COLUMN_IMG_RECIPES BLOB," +
                "$COLUMN_INGREDIENTS_RECIPES TEXT," +
                "$COLUMN_DIRECTIONS_RECIPES TEXT," +
                "FOREIGN KEY($COLUMN_ID_USER_RECIPES) REFERENCES  $TABLE_NAME_USER($COLUMN_ID_USER))")
        db?.execSQL(CREATE_TABLE_RECIPES)

        val CREATE_TABLE_FAVORITES = ("CREATE TABLE $TABLE_NAME_FAVORITES (" +
                "$COLUMN_ID_FAVORITES INTEGER PRIMARY KEY," +
                "$COLUMN_ID_USER_FAVORITES INTEGER," + // Added missing comma
                "$COLUMN_ID_RECIPES_FAVORITES INTEGER," + // Added missing comma
                "FOREIGN KEY($COLUMN_ID_USER_FAVORITES) REFERENCES  $TABLE_NAME_USER($COLUMN_ID_USER)," +
                "FOREIGN KEY($COLUMN_ID_RECIPES_FAVORITES) REFERENCES  $TABLE_NAME_RECIPES($COLUMN_ID_RECIPES))")
        db?.execSQL(CREATE_TABLE_FAVORITES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_USER")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_RECIPES")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_FAVORITES")
        onCreate(db)
    }

    //users
    fun addUser(firstName: String, lastName: String, email: String, password: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_FIRST_NAME_USER, firstName)
        values.put(COLUMN_LAST_NAME_USER, lastName)
        values.put(COLUMN_EMAIL_USER, email)
        values.put(COLUMN_PASSWORD_USER, password)
        val id = db.insert(TABLE_NAME_USER, null, values)
        db.close()
        return id
    }

    fun getUserByEmail(email: String): Cursor? {
        val db = this.readableDatabase
        var data = db.rawQuery("SELECT * FROM $TABLE_NAME_USER", arrayOf())
        return db.rawQuery("SELECT * FROM $TABLE_NAME_USER WHERE $COLUMN_EMAIL_USER = ?", arrayOf(email))
    }

    fun getUsersByEmailAndPassword(email: String, password: String): Cursor? {
        val db = this.readableDatabase
        var data = db.rawQuery("SELECT * FROM $TABLE_NAME_USER", arrayOf())
        return db.rawQuery("SELECT * FROM $TABLE_NAME_USER WHERE $COLUMN_EMAIL_USER = ? AND $COLUMN_PASSWORD_USER = ?", arrayOf(email, password))
    }

    @SuppressLint("Range")
    fun getName(email: String, password: String): String? {
        val db = this.readableDatabase
        var firstName: String? = null
        val cursor = db.rawQuery(
            "SELECT $COLUMN_FIRST_NAME_USER FROM $TABLE_NAME_USER WHERE $COLUMN_EMAIL_USER = ? AND $COLUMN_PASSWORD_USER = ?",
            arrayOf(email, password)
        )
        if (cursor != null && cursor.moveToFirst()) {
            firstName = cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME_USER))
        }
        cursor?.close()
        return firstName
    }

    @SuppressLint("Range")
    fun getId(email: String, password: String): Int? {
        val db = this.readableDatabase
        var id: Int? = null
        val cursor = db.rawQuery(
            "SELECT $COLUMN_ID_USER FROM $TABLE_NAME_USER WHERE $COLUMN_EMAIL_USER = ? AND $COLUMN_PASSWORD_USER = ?",
            arrayOf(email, password)
        )
        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_USER))
        }
        cursor?.close()
        return id
    }

    // recipes
    fun addRecipe(userId: Int?, name: String, byteArray: ByteArray, ingredients: String, directions: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID_USER_RECIPES, userId)
        values.put(COLUMN_NAME_RECIPES, name)
        values.put(COLUMN_IMG_RECIPES, byteArray)
        values.put(COLUMN_INGREDIENTS_RECIPES, ingredients)
        values.put(COLUMN_DIRECTIONS_RECIPES, directions)
        val id = db.insert(TABLE_NAME_RECIPES, null, values)
        db.close()
        return id
    }
    fun addToFavorites(userId: Long, recipeId: Long): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID_USER_FAVORITES, userId)
        values.put(COLUMN_ID_RECIPES_FAVORITES, recipeId)
        val id = db.insert(TABLE_NAME_FAVORITES, null, values)
        db.close()
        return id
    }
}
