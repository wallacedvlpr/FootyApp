package com.example.footyapp.model

data class IndividualTeamResponse (
    val success: Boolean,
    val pager: Pager,
    val data: List<ClubItem>)
