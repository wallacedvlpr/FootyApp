package com.example.footyapp.model

/**
 * League List Response
 * URL: https://api.footystats.org/league-list?key=example
 */
data class LeagueListResponse(
    val success: Boolean,
    val pager: List<Pager>,
    val data: List<League>
)

data class League (
    val name: String,
    val season: List<Season>
)

data class Season(
    val id: Int,
    val year: Int
)

