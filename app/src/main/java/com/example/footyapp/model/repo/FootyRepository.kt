package com.example.footyapp.model.repo


import androidx.lifecycle.LiveData
import com.example.footyapp.model.SingleTeamResponse
import com.example.footyapp.model.LeagueListResponse
import com.example.footyapp.model.LeagueTeamsResponse
import com.example.footyapp.model.network.NetCalls

class FootyRepository private constructor(private val netCalls: NetCalls ) {

    fun getTeams():LiveData<LeagueTeamsResponse> = netCalls.getTeams()
    fun getOneTeam(id: Int): LiveData<SingleTeamResponse>  = netCalls.getOneTeam(id)
    fun getLeagues(): LiveData<LeagueListResponse>  = netCalls.getLeagues()

    // Single Instance of this class
    companion object{
        @Volatile private var instance: FootyRepository? = null

        fun getInstance(netCalls: NetCalls) =
            instance ?: synchronized(this){
                instance
                    ?: FootyRepository(netCalls)
                        .also { instance = it }
            }
    }
}