package com.example.footyapp.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.footyapp.R
import com.example.footyapp.model.ClubItem
import com.example.footyapp.model.db.Favorite
import com.example.footyapp.utils.InjectorUtils
import com.example.footyapp.utils.LiveDataConnection
import com.example.footyapp.viewmodel.FavoritesViewModel
import com.example.footyapp.viewmodel.FootyViewModel
import kotlinx.android.synthetic.main.activity_detailed_club.*

class DetailedClubActivity : AppCompatActivity() {

    private val factory2 by lazy {
        InjectorUtils.provideFavoritesViewModelFactory(application)
    }
    private val favViewModel by viewModels<FavoritesViewModel>{
        factory2
    }
    private val factory = InjectorUtils.provideFootyViewModelFactory()
    private val liveDataConnection by lazy {
        LiveDataConnection.getInstance(this)
    }
    private val viewModel by viewModels<FootyViewModel>{
        factory
    }
    var id: Int = 0
    lateinit var club: ClubItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_club)
        onConnected()
    }

    private fun onConnected(){
        liveDataConnection.isConnectionLive().observe(this, Observer{
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
        id = intent.getIntExtra("Club_ID", 59)
        viewModel.getOneTeam(id).observe(this, Observer {response ->
            club =findSeason(response.data)
            initViews()
        })
    }
    private fun init() {
        netCall()
    }

    private fun findSeason(clubs: List<ClubItem>):ClubItem {
        return clubs.find {
            it.season == "2017/2018" && it.season_format == "Domestic League"
        } ?: clubs[0] // null return first available season for the club
    }
    private fun initViews(){
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

        btn_add_to_favorites.setOnClickListener {
            addToFavorites()
        }
    }

    fun addToFavorites() {
        favViewModel.addFavorites(
            Favorite(id, club.full_name, 't')
        )
        finish()
    }
}