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
        viewModel.getOneTeam(id).observe(this, Observer {response ->
            initViews(find2017DomesticSeason(response.data))
        })
    }
    private fun init() {
        netCall()
    }

    private fun find2017DomesticSeason(clubs: List<ClubItem>):ClubItem {
        return clubs.find {
            it.season == "2017/2018" && it.season_format == "Domestic League"
        } ?: clubs[0]
    }
    private fun initViews(club: ClubItem){
        club_detail_club_name.text = club.full_name

        val stringFounded = ", est. ${club.founded}"
        club_detail_founded.text = stringFounded

        val stringCountry = "Country: ${club.country}"
        club_detail_country.text = stringCountry

        val stringSeason = "${club.season} Season"
        club_detail_season.text = stringSeason

        val stringPos = "Table Position: ${club.table_position}"
        club_detail_table_position.text =  stringPos

        val stringDraws = "D: ${club.stats.seasonDrawsNum_overall}"
        club_detail_draws.text = stringDraws

        val stringWins= "Wins: ${club.stats.seasonWinsNum_overall}"
        club_detail_wins.text = stringWins

        val stringLosses = "Losses: ${club.stats.seasonLossesNum_overall}"
        club_detail_losses.text = stringLosses
    }

    override fun onStop() {
        liveDataConnection.unregisterCallBack()
        super.onStop()
    }
}