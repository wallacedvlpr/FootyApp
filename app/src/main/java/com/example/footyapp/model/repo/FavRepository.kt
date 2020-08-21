package com.example.footyapp.model.repo

import com.example.footyapp.model.db.Favorite
import com.example.footyapp.model.db.MockFavDao

class FavRepository private constructor(private val favDao: MockFavDao){
    fun addFavorite(fav: Favorite){
        favDao.addFavorite(fav)
    }
    fun getFavorites() = favDao.getFavorites()

    // Single Instance of this class
    companion object{
        @Volatile private var instance: FavRepository? = null

        fun getInstance(favDao: MockFavDao) =
            instance ?: synchronized(this){
                instance
                    ?: FavRepository(favDao)
                        .also { instance = it }
            }
    }
}