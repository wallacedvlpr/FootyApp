package com.example.footyapp.viewmodel


import androidx.lifecycle.ViewModel
import com.example.footyapp.model.repo.FootyRepository

class FootyViewModel (private val footyRepository: FootyRepository
): ViewModel() {

    fun getTeams(id: Int = 2012) = footyRepository.getTeams(id)
    fun getOneTeam(id: Int) = footyRepository.getOneTeam(id)
    fun getLeagues() = footyRepository.getLeagues()

    override fun onCleared() {
        super.onCleared()
    }
}