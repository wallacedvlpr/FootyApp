package com.example.footyapp.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.footyapp.R
import com.example.footyapp.model.ClubItem
import com.example.footyapp.model.SingleTeamResponse
import com.example.footyapp.model.network.FootyNetworkCall
import com.example.footyapp.utils.InjectorUtils
import com.example.footyapp.utils.LiveDataConnection
import com.example.footyapp.viewmodel.FootyViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detailed_club.*

class DetailedClubActivity : AppCompatActivity() {

    private val factory = InjectorUtils.provideFootyViewModelFactory()
    private val liveDataConnection by lazy {
        LiveDataConnection.getInstance(applicationContext)
    }
    private val viewModel by viewModels<FootyViewModel>{
        factory
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_club)
        onConnected()
    }

    private fun onConnected(){
        liveDataConnection.isConnected().observe(this, Observer{
            showViews(it)
        })
    }

    private fun showViews(bool: Boolean) {
        if(bool){
            tv_no_connection_club_details.visibility = View.INVISIBLE
            tv_no_connection_club_details.setTextSize(TypedValue.COMPLEX_UNIT_SP, 0F)
            init()
        }else {
            tv_no_connection_club_details.visibility = View.VISIBLE
            tv_no_connection_club_details.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.0F)
        }
    }
    private fun netCall(){
        val id = intent.getIntExtra("Club_ID", 59)
        FootyNetworkCall.getRetrofit().getOneTeam(id = id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : io.reactivex.rxjava3.core.Observer<SingleTeamResponse> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onNext(t: SingleTeamResponse) {
                        initViews(t.data.find { it.season == "2017/2018" && it.season_format == "Domestic League"}?: t.data[0])
                    }

                    override fun onError(e: Throwable?) {
                        Log.d("Home", e?.message?:"Error but null")
                    }
                }
            )
    }
    private fun init() {
        netCall()
    }

    private fun initViews(club: ClubItem){
        val name: TextView = findViewById(R.id.club_detail_club_name)
        val founded: TextView = findViewById(R.id.club_detail_founded)
        val country: TextView = findViewById(R.id.club_detail_country)
        val season: TextView = findViewById(R.id.club_detail_season)

        name.text = club.full_name
        val stringFounded = ", est. ${club.founded}"
        founded.text = stringFounded
        val stringCountry = "Country: ${club.country}"
        country.text = stringCountry
        val stringSeason = "${club.season} Season"
        season.text = stringSeason
        val stringPos = "Table Position: ${club.table_position}"
        club_detail_table_position.text =  stringPos
        val stringDraws = "D: ${club.stats.seasonDrawsNum_overall}"
        val stringWins= "Wins: ${club.stats.seasonWinsNum_overall}"
        val stringLosses = "Losses: ${club.stats.seasonLossesNum_overall}"
        club_detail_losses.text = stringLosses
        club_detail_wins.text = stringWins
        club_detail_draws.text = stringDraws
    }
    override fun onStop() {
        liveDataConnection.unregisterCallBack()
        super.onStop()
    }
}