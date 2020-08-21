package com.example.footyapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.footyapp.model.repo.FavRepository
import com.example.footyapp.model.db.Favorite

class FavoritesViewModel(private val favRepository: FavRepository)
    :ViewModel(){

    fun getFavorites() = favRepository.getFavorites()
    fun addFavorites(favorite: Favorite) = favRepository.addFavorite(favorite)
}