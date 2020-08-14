package com.example.footyapp.network

import com.example.footyapp.model.LeagueListResponse
import com.example.footyapp.model.LeagueTeamsResponse
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface FootyNetworkCall {

    companion object{
        fun getRetrofit(): FootyNetworkCall {
            return Retrofit.Builder()
                .baseUrl("https://api.footystats.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build().create(FootyNetworkCall::class.java)
        }
    }
    /**
     * League List:-
     * ~network call to display list of leagues
     */
    @GET(value = "league-list")
    fun getLeagues(
        @Query(value = "key")
        key: String="example"): Observable<LeagueListResponse>

    /**
     * League teams
     * ~network call to display teams of league
     * league-teams?
     * key=example
     * &season_id=2012
     * &include=stats
     */
    @GET(value = "league-teams")
    fun getTeams(
        @Query(value = "key")key: String="example",
        @Query(value = "season_id")season_id: Int,
        @Query(value= "include")include:String ="stats"): Observable<LeagueTeamsResponse>
/*
    @GET(value = "league-list")
    fun getLeaguePlayers(
        @Query(value = "key")key: String="example"): Observable<LeaguesListResponse>

    @GET(value = "league-list")
    fun getLeagueStats(
        @Query(value = "key")key: String="example"): Observable<LeaguesListResponse>

    @GET(value = "league-list")
    fun getIndividualTeam(
        @Query(value = "key")key: String="example"): Observable<LeaguesListResponse>*/
}