package com.example.footyapp.model.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
@Dao
interface FavoriteDao {
    @Query("SELECT * from fav_table ORDER BY fav_name ASC")
    fun getAllFavorites():LiveData<List<Favorite>>

    @Insert
    suspend fun insert( vararg favorites: Favorite)

    @Delete
    suspend fun delete(favorite: Favorite)
}