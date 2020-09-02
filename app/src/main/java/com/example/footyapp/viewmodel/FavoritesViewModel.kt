package com.example.footyapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.footyapp.model.db.FavDatabase
import com.example.footyapp.model.repo.FavRepository
import com.example.footyapp.model.db.Favorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application)
    :AndroidViewModel(application){
    private val favRepository: FavRepository
    val allFavorites: LiveData<List<Favorite>>

    init {
        val favoriteDao = FavDatabase.getInstance(application, viewModelScope).favDAO()
        favRepository = FavRepository(favoriteDao)
        allFavorites = favRepository.allFavorites
    }
    fun addFavorites(favorite: Favorite) = viewModelScope.launch(Dispatchers.IO){
        favRepository.addFavorite(favorite)
    }
    fun deleteFavorite(favorite: Favorite) = viewModelScope.launch(Dispatchers.IO) {
        favRepository.deleteFavorite(favorite)
    }
}