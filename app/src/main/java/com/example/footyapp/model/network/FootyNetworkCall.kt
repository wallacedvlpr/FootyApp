package com.example.footyapp.model.network

import android.content.Context
import com.example.footyapp.model.SingleTeamResponse
import com.example.footyapp.model.LeagueListResponse
import com.example.footyapp.model.LeagueTeamsResponse
import com.example.footyapp.utils.CustomApp
import com.example.footyapp.utils.LiveDataConnection
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Observable
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface FootyNetworkCall {
    /**
     * League List:-
     * ~network call to display list of leagues
     */
    @GET(value = "league-list")
    fun getLeagues(
        @Query(value = "key")
        key: String="example"): Observable<Response<LeagueListResponse>>

    /**
     * League teams
     * ~network call to display teams of league
     */
    @GET(value = "league-teams")
    fun getTeams(
        @Query(value = "key")key: String="example",
        @Query(value = "season_id")season_id: Int,
        @Query(value= "include")include: String ="stats"): Observable<Response<LeagueTeamsResponse>>

    /**
     * League Team
     * ~network call to display a team
     */
    @GET(value = "team")
    fun getOneTeam(
        @Query(value = "key")key: String="example",
        @Query(value = "team_id")id: Int): Observable<Response<SingleTeamResponse>>
/*
    @GET(value = "league-list")
    fun getLeagueStats(
        @Query(value = "key")key: String="example"): Observable<LeaguesListResponse>

    @GET(value = "league-list")
    fun getIndividualTeam(
        @Query(value = "key")key: String="example"): Observable<LeaguesListResponse>
 */
}