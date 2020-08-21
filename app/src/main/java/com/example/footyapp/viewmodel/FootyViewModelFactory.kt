package com.example.footyapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.footyapp.model.repo.FootyRepository

class FootyViewModelFactory (private val footyRepository: FootyRepository)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FootyViewModel(footyRepository) as T
    }
}