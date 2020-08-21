package com.example.footyapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.footyapp.model.repo.FootyRepository

class FootyViewModel (private val footyRepository: FootyRepository): ViewModel() {

    fun getTeams() = footyRepository.getTeams()
    fun getOneTeam(id: Int) = footyRepository.getOneTeam(id)
    fun getLeagues() = footyRepository.getLeagues()
}