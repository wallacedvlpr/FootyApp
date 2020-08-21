package com.example.footyapp.model

/**
 * Team - Individual - https://api.footystats.org/team?key=example&team_id=93
 *
 */
data class SingleTeamResponse (
    val success: Boolean,
    val pager: Pager,
    val data: List<ClubItem>)