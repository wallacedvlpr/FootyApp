package com.example.footyapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.footyapp.model.db.FavRepository

class FavoritesViewModelFactory(private val favRepository: FavRepository)
    :ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavoritesViewModel(favRepository) as T
    }
}