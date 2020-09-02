package com.example.footyapp.model.network

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.footyapp.model.SingleTeamResponse
import com.example.footyapp.model.LeagueListResponse
import com.example.footyapp.model.LeagueTeamsResponse
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetCalls private constructor(){
    private val baseURL = "https://api.footystats.org/"

    private fun getRetrofit(): FootyNetworkCall {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build().create(FootyNetworkCall::class.java)
    }

    fun getLeagues(): MutableLiveData<LeagueListResponse>{
        val mLiveData = MutableLiveData<LeagueListResponse>()
        getRetrofit().getLeagues()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<LeagueListResponse> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onNext(t: LeagueListResponse) {
                        Log.d("test", "Hello")
                        mLiveData.value = t
//                        if (t.raw().cacheResponse != null) {
//                            mLiveData.value = t.body()
//                            Log.d("Network", "response came from cache")
//                        }else {
//                            mLiveData.value = t.body()
//                            Log.d("Network", "response came from Network")
//                        }

                    }

                    override fun onError(e: Throwable?) {
                        Log.d("Home", e?.message?:"Error but null")
                    }
                }
            )
        return mLiveData
    }

    fun getTeams(id: Int = 2012): MutableLiveData<LeagueTeamsResponse>{
        val mLiveData = MutableLiveData<LeagueTeamsResponse>()
        getRetrofit().getTeams(season_id = id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<LeagueTeamsResponse>{
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
        getRetrofit().getOneTeam(id = id )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer <SingleTeamResponse>{
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onNext(t: SingleTeamResponse) {
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

//    private val cacheSize = (5 * 1024 * 1024).toLong() // 5 MB
//    private val cache = Cache(context.cacheDir, cacheSize)
//
//    private val networkCacheInterceptor = Interceptor{ chain ->
//        var request = chain.request()
//
//        request = if(LiveDataConnection.getInstance(CustomApp.footyContext!!)
//                .isConnected()) {
//            val cacheControl = CacheControl.Builder()
//                .maxAge(5, TimeUnit.SECONDS)
//                .build()
//            request.newBuilder().header("Cache-Control",
//                cacheControl.toString())
//                .build()
//        }
//        else {
//            val cacheControl = CacheControl.Builder()
//                .maxStale(7, TimeUnit.DAYS)
//                .onlyIfCached()
//                .build()
//            request.newBuilder().header("Cache-Control", cacheControl.toString())
//                .build()
//        }
//        chain.proceed(request)
//    }
//    private val httpClient = OkHttpClient.Builder()
//        .cache(cache)
//        .addNetworkInterceptor(networkCacheInterceptor)
//        .build()

}