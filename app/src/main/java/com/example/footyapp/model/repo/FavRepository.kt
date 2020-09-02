package com.example.footyapp.model.repo

import androidx.lifecycle.LiveData
import com.example.footyapp.model.db.Favorite
import com.example.footyapp.model.db.FavoriteDao

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class FavRepository (private val favDao: FavoriteDao){

    // Room executes all queries on a separate thread
    // Observed LiveData will notify the observer when the data has changed.
    val allFavorites: LiveData<List<Favorite>> = favDao.getAllFavorites()

    suspend fun addFavorite(fav: Favorite){
        favDao.insert(fav)
    }
    //fun getFavorites() = favDao.getAllFavorites()
    suspend fun  deleteFavorite(fav: Favorite){
        favDao.delete(fav)
    }
    // Single Instance of this class
    companion object{
        @Volatile private var instance: FavRepository? = null

        fun getInstance(favDao: FavoriteDao) =
            instance ?: synchronized(this){
                instance
                    ?: FavRepository(favDao)
                        .also { instance = it }
            }
    }
}