package com.example.footyapp.model.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.footyapp.model.SingleTeamResponse
import com.example.footyapp.model.LeagueListResponse
import com.example.footyapp.model.LeagueTeamsResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class NetCalls private constructor(){

    fun getLeagues(): MutableLiveData<LeagueListResponse>{
        val mLiveData = MutableLiveData<LeagueListResponse>()
        FootyNetworkCall.getRetrofit().getLeagues()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<LeagueListResponse> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onNext(t: LeagueListResponse) {
                        mLiveData.value = t
                    }

                    override fun onError(e: Throwable?) {
                        Log.d("Home", e?.message?:"Error but null")
                    }
                }
            )
        return mLiveData
    }

    fun getTeams(): MutableLiveData<LeagueTeamsResponse>{
        val mLiveData = MutableLiveData<LeagueTeamsResponse>()
        FootyNetworkCall.getRetrofit().getTeams(season_id = 2012)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<LeagueTeamsResponse> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onNext(t: LeagueTeamsResponse) {
                        mLiveData.value = t
                    }

                    override fun onError(e: Throwable?) {
                        Log.d("Home", e?.message?:"Error but null")
                    }
                }
            )
        return mLiveData
    }

    fun getOneTeam(id: Int): MutableLiveData<SingleTeamResponse>{
        val mLiveData = MutableLiveData<SingleTeamResponse>()
        FootyNetworkCall.getRetrofit().getOneTeam(id = id )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer <SingleTeamResponse>{
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onNext(t: SingleTeamResponse?) {
                        mLiveData.value = t
                    }

                    override fun onError(e: Throwable?) {
                        Log.d("Home", e?.message?:"Error but null")
                    }

                }
            )
        return mLiveData
    }
    // Single Instance of this class
    companion object{
        @Volatile private var instance: NetCalls? = null

        fun getInstance() =
            instance ?: synchronized(this){
                instance ?: NetCalls().also { instance = it }
            }
    }
}