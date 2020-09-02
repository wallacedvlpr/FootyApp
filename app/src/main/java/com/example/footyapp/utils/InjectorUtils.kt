package com.example.footyapp.utils

import android.app.Application
import android.content.Context
import com.example.footyapp.model.repo.FavRepository
import com.example.footyapp.model.db.FavDatabase
import com.example.footyapp.model.network.NetCalls
import com.example.footyapp.model.repo.FootyRepository
import com.example.footyapp.viewmodel.FavoritesViewModelFactory
import com.example.footyapp.viewmodel.FootyViewModelFactory

object InjectorUtils {
    fun provideFavoritesViewModelFactory(application: Application): FavoritesViewModelFactory{
        return FavoritesViewModelFactory(application)
    }

    fun provideFootyViewModelFactory(): FootyViewModelFactory{
        val footyRepository = FootyRepository.getInstance(NetCalls.getInstance())
        return FootyViewModelFactory(footyRepository)
    }
}