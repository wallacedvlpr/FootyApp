package com.example.footyapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.footyapp.R
import com.example.footyapp.model.ClubItem
import com.example.footyapp.model.IndividualTeamResponse
import com.example.footyapp.network.FootyNetworkCall
import com.example.footyapp.network.NetworkConnection
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detailed_club.*

class DetailedClubActivity : AppCompatActivity() {
    //lateinit var club: List<ClubItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_club)
        testConnection()
    }


    private fun testConnection(){
        val id = intent.getIntExtra("Club_ID", 59)
        val networkConnection = NetworkConnection.getInstance(applicationContext)
        networkConnection?.testConnection(this,
            tv_no_connection_club_details
        ) { init(id) }
    }
    private fun netCall(_id: Int){
        FootyNetworkCall.getRetrofit().getOneTeam(id = _id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : io.reactivex.rxjava3.core.Observer<IndividualTeamResponse> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onNext(t: IndividualTeamResponse) {
                        initViews(t.data.find { it.season == "2017/2018" && it.season_format == "Domestic League"}?: t.data[0])
                    }

                    override fun onError(e: Throwable?) {
                        Log.d("Home", e?.message?:"Error but null")
                    }
                }
            )
    }
    private fun init(i: Int) {
        netCall(i)
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
}