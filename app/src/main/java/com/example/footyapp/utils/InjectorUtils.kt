package com.example.footyapp.utils

import android.app.Application
import android.content.Context
import com.example.footyapp.model.repo.FavRepository
import com.example.footyapp.model.db.MockFavDatabase
import com.example.footyapp.model.network.NetCalls
import com.example.footyapp.model.repo.FootyRepository
import com.example.footyapp.viewmodel.FavoritesViewModelFactory
import com.example.footyapp.viewmodel.FootyViewModelFactory

object InjectorUtils {
    fun provideFavoritesViewModelFactory(): FavoritesViewModelFactory{
        val favRepository = FavRepository.getInstance(MockFavDatabase.getInstance().favDao)
        return FavoritesViewModelFactory(favRepository)
    }

    fun provideFootyViewModelFactory(context: Context): FootyViewModelFactory{
        val footyRepository = FootyRepository.getInstance(NetCalls.getInstance(context))
        return FootyViewModelFactory(footyRepository)
    }
}