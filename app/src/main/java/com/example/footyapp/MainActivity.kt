package com.example.footyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.footyapp.model.FootyNetworkCall
import com.example.footyapp.model.LeagueListResponse
import com.example.footyapp.view.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        testConnection()
    }
    private fun testConnection(){
        //networkConnection Class
        val networkConnection = NetworkConnection.getInstance(applicationContext)
        networkConnection?.let {
            it.observe(this, Observer { isConnected ->
                if (!isConnected) {
                    tv_no_connection.visibility = View.VISIBLE
                    unInit()
                } else {
                    tv_no_connection.visibility = View.GONE
                    init()
                    networkCalls()
                }
            })
        }
    }
    private fun networkCalls(){
        FootyNetworkCall.getRetrofit().getLeagues()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : io.reactivex.rxjava3.core.Observer<LeagueListResponse> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable?) {
                        //Todo
                    }

                    override fun onNext(t: LeagueListResponse) {
                        if(t.success)
                            Toast.makeText(this@MainActivity,
                                t.data[0].name, Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable?) {
                        Log.d("Home", e?.message?:"Error but null")
                    }

                }
            )
    }
    private fun unInit(){
        pager.visibility = View.GONE
    }
    private fun init(){
        pager.adapter = ViewPagerAdapter(this)
        pager.isUserInputEnabled = false
        TabLayoutMediator(tab_layout, pager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when(position){
                    0-> {
                        tab.text = "Leagues"
                    }
                    1-> {
                        tab.text = "Clubs"
                    }
                    2-> {
                        tab.text = "Favorites"
                    }
                    else ->   Exception("Error in the TabLayout").printStackTrace()
                }
            }).attach()
    }
}