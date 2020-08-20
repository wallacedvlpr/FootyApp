package com.example.footyapp.model.db

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
                instance?: FavRepository(favDao).also { instance = it }
            }
    }
}