package com.example.footyapp.model
//League Matches - https://api.footystats.org/league-matches?key=example&league_id=2012
//League Players - https://api.footystats.org/league-players?key=example&season_id=2012
//League Stats - https://api.footystats.org/league-season?key=example&season_id=2012
// Team - Last 5/6/10 stats - https://api.footystats.org/lastx?key=example&team_id=59
//Team - Individual - https://api.footystats.org/team?key=example&team_id=93
//League Table - https://api.footystats.org/league-tables?key=example&season_id=2012
/**
 * League Teams Response
 * URL https://api.footystats.org/league-teams?key=example&season_id=2012&include=stats
 */
data class LeagueTeamsResponse (
    val success: Boolean,
    val data: List<ClubItem>,
    val pager: Pager,
    val message: String)

data class Pager(
    val current_page: Int,
    val max_page: Int,
    val results_per_page: Int,
    val total_results: Int
)

data class ClubItem(
    val id: Int,
    val name: String,
    val cleanName: String,
    val english_name: String,
    val shortHand: String,
    val country: String,
    val founded: Int,
    val image: String,
    val season: String,
    val stadium_name: String,
    val stadium_address: String,
    val table_position: Int,
    val performance_rank: Int,
    val season_format: String,
    val competition_id: Int,
    val full_name: String,
    val alt_names: List<String>,
    val stats: Stats
)

data class Stats(
    val suspended_matches: Int,
    val homeAttackAdvantage: Int,
    val homeDefenceAdvantage: Int,
    val homeOverallAdvantage: Int,
    val seasonGoals_overall: Int,
    val seasonConceded_overall: Int,
    val seasonGoalsTotal_overall: Int,
    val seasonGoalsTotal_home: Int,
    val seasonGoalsTotal_away: Int,
    val seasonScoredNum_overall: Int,
    val seasonScoredNum_home: Int,
    val seasonScoredNum_away: Int,
    val seasonConcededNum_overall: Int,
    val seasonConcededNum_home: Int,
    val seasonConcededNum_away: Int,
    val seasonGoalDifference_overall: Int,
    val seasonGoalDifference_home: Int,
    val seasonGoalDifference_away: Int,
    val seasonWinsNum_overall: Int,
    val seasonWinsNum_home: Int,
    val seasonWinsNum_away: Int,
    val seasonDrawsNum_overall: Int,
    val seasonDrawsNum_home: Int,
    val seasonDrawsNum_away: Int,
    val seasonLossesNum_overall: Int,
    val seasonLossesNum_home: Int,
    val seasonLossesNum_away: Int,
    val seasonMatchesPlayed_overall: Int,
    val seasonMatchesPlayed_away: Int,
    val seasonMatchesPlayed_home: Int,
    val seasonHighestScored_home: Int,
    val seasonHighestConceded_home: Int,
    val seasonHighestScored_away: Int,
    val seasonHighestConceded_away: Int,
    val seasonPPG_overall: Double,
    val seasonPPG_home: Double,
    val seasonPPG_away: Double,
    val winPercentage_overall: Int,
    val winPercentage_home: Int,
    val winPercentage_away: Int
)
