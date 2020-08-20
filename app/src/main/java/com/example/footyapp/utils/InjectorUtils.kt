package com.example.footyapp.utils

import com.example.footyapp.model.db.FavRepository
import com.example.footyapp.model.db.MockFavDatabase
import com.example.footyapp.viewmodel.FavoritesViewModelFactory

object InjectorUtils {
    fun provideFavoritesViewModelFactory(): FavoritesViewModelFactory{
        val favRepository = FavRepository.getInstance(MockFavDatabase.getInstance().favDao)
        return FavoritesViewModelFactory(favRepository)
    }
}