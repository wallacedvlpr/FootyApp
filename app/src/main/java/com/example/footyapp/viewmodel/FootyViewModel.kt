package com.example.footyapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.footyapp.model.LeagueListResponse
import com.example.footyapp.network.FootyNetworkCall
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class FootyViewModel (): ViewModel() {

    fun getListOfLeagues(){
        FootyNetworkCall.getRetrofit().getLeagues()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : io.reactivex.rxjava3.core.Observer<LeagueListResponse> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onNext(t: LeagueListResponse) {
                       //TODO pass data on to a view or fragment
                    }

                    override fun onError(e: Throwable?) {
                        Log.d("Home", e?.message?:"Error but null")
                    }

                }
            )

    }
}