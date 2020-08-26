package com.example.footyapp.view.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.footyapp.utils.LiveDataConnection
import com.example.footyapp.R
import com.example.footyapp.model.ClubItem
import com.example.footyapp.model.LeagueTeamsResponse
import com.example.footyapp.model.network.FootyNetworkCall
import com.example.footyapp.utils.InjectorUtils
import com.example.footyapp.viewmodel.FootyViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_league.*

class LeagueActivity : AppCompatActivity(){
    lateinit var clubs: List<ClubItem>
    private lateinit var table: TableLayout
    private lateinit var leagueName: TextView
    private val liveDataConnection by lazy {
        LiveDataConnection.getInstance(applicationContext)
    }

    private val factory = InjectorUtils.provideFootyViewModelFactory()

    private val viewModel by viewModels<FootyViewModel>{
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_league)
        onConnected()
    }
    private fun showViews(bool: Boolean) {
        if(bool){
            tv_no_connection_league.visibility = View.INVISIBLE
            tv_no_connection_league.setTextSize(TypedValue.COMPLEX_UNIT_SP, 0F)
            init()
        }else {
            tv_no_connection_league.visibility = View.VISIBLE
            tv_no_connection_league.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.0F)
        }
    }

    private fun onConnected(){
        liveDataConnection.isConnected().observe(this, Observer{
            showViews(it)
        })
    }
    private fun netCall(){
        val id = intent.getIntExtra("League_Id", 2012)

        viewModel.getTeams(id).observe(
            this,
            Observer{ response->
                clubs = response.data.sortedWith(compareBy { it.table_position})
                initTableLayout()
                initTableName("${response.data[0].country}'s Tier 1 League ")
            }
        )
    }
    private fun init() {
        netCall()
    }
    private fun initTableName(name: String){
        leagueName = findViewById(R.id.tv_league_name)
        leagueName.text = name
    }

    private fun setParams(number: Float) =
        TableRow.LayoutParams(
            0,
            TableRow.LayoutParams.WRAP_CONTENT, number
        )
    private fun initTableLayout(){
        table = findViewById(R.id.league_activity_table_layout)
        initTableRows(clubs)
    }

    private fun initTableRows(listOfClubs: List<ClubItem>) {
        listOfClubs.forEachIndexed { index, club ->

            val tr = TableRow(this)
            tr.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )

            //position
            val tablePos = TextView(this)
            tablePos.apply {
                setTextColor(when(club.table_position){
                    in 1..4 -> Color.BLUE
                    in 5..6 -> Color.GREEN
                    in 18..20 -> Color.RED
                    else -> Color.DKGRAY
                })
                text = club.table_position.toString()
                layoutParams = setParams(1F)
            }

            //team
            val team = TextView(this)
            team.apply {
                text = club.full_name
                layoutParams = setParams(8F)
            }

            //played
            val gamesPlayed = TextView(this)
            gamesPlayed.apply {
                text = club.stats.seasonMatchesPlayed_overall.toString()
                layoutParams = setParams(1F)
            }

            //wins
            val wins = TextView(this)
            wins.apply {
                text = club.stats.seasonWinsNum_overall.toString()
                layoutParams = setParams(1F)
            }

            //losses
            val losses = TextView(this)
            losses.apply {
                text = club.stats.seasonLossesNum_overall.toString()
                layoutParams = setParams(1F)
            }

            //goals
            val goals = TextView(this)
            val g = "${club.stats.seasonGoals_overall} : ${club.stats.seasonConcededNum_overall}"
            goals.apply {
                text = g
                setPadding(2, 0, 1, 0)
                layoutParams = setParams(4F)
            }

            //points
            val points = TextView(this)
            points.apply {
                text =
                    (club.stats.seasonWinsNum_overall * 3 + club.stats.seasonDrawsNum_overall)
                        .toString()
                layoutParams = setParams(2F)
            }
            //Add views
            tr.apply {
                addView(tablePos)
                addView(team)
                addView(gamesPlayed)
                addView(wins)
                addView(losses)
                addView(goals)
                addView(points)
            }
            table.addView(tr, index + 1)
        }
    }

    override fun onStop() {
        liveDataConnection.unregisterCallBack()
        super.onStop()
    }
    override fun onBackPressed() {
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
        super.onBackPressed()
    }
}