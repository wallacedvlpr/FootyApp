package com.example.footyapp.model.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MockFavDao{
    private val favList = mutableListOf<Favorite>(
        Favorite(59,"Arsenal FC", 't'),
        Favorite(93, "Manchester City FC", 't'),
        Favorite(149, "Manchester United FC", 't')
    )
    private val liveFaves = MutableLiveData<List<Favorite>>()

    init {
        liveFaves.value = favList
    }

    fun addFavorite(fav: Favorite){
        favList.add(fav)
        liveFaves.value = favList
    }
    fun getFavorites() = liveFaves as LiveData<List<Favorite>>
}