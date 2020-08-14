package com.example.footyapp.view.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import com.example.footyapp.network.NetworkConnection
import com.example.footyapp.R
import com.example.footyapp.model.ClubItem
import com.example.footyapp.model.LeagueTeamsResponse
import com.example.footyapp.network.FootyNetworkCall
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_league.*

class LeagueActivity : AppCompatActivity(){
    lateinit var clubs: List<ClubItem>
    lateinit var table: TableLayout
    lateinit var leagueName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_league)
        testConnection()
    }

    private fun testConnection(){
        val id = intent.getIntExtra("League_Id", 2012)
        val networkConnection = NetworkConnection.getInstance(applicationContext)
        networkConnection?.testConnection(this,
        tv_no_connection_league
        ){ init(id) }
    }
    private fun netCall(i: Int){
        FootyNetworkCall.getRetrofit().getTeams(season_id = i)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : io.reactivex.rxjava3.core.Observer<LeagueTeamsResponse> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onNext(t: LeagueTeamsResponse) {
                        Log.i("LeagueActivity", t.data[0].country)
                        clubs = t.data.sortedWith(compareBy { it.table_position})
                        initTableLayout()
                        initTableName("${t.data[0].country}'s Top League ")
                    }

                    override fun onError(e: Throwable?) {
                        Log.e("League Activity", e?.message?:"Error but null")
                    }
                }
            )
    }
    private fun init(id: Int) {
        netCall(id)
        Log.d("League Activty", "$id, id")
    }
    private fun initTableName(name: String){
        leagueName = findViewById(R.id.tv_league_name)
        leagueName.text = name
    }
    private fun initTableLayout(){
        table = findViewById(R.id.league_activity_table_layout)
        clubs.forEachIndexed { index, it ->
            val tr = TableRow(this)
            tr.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT)
            //position
            val tablePos = TextView(this)
            tablePos.text = it.table_position.toString()
            tablePos.setTextColor (if (it.table_position in 1..4) Color.BLUE else if (it.table_position in 5..6) Color.GREEN else if (it.table_position in 18..20) Color.RED else Color.DKGRAY)
            tablePos.layoutParams = TableRow.LayoutParams( 0,TableRow.LayoutParams.WRAP_CONTENT, 1F)
            //team
            val team = TextView(this)
            team.text = it.full_name
            team.layoutParams = TableRow.LayoutParams( 0,TableRow.LayoutParams.WRAP_CONTENT, 8F)
            //played
            val gamesPlayed = TextView(this)
            gamesPlayed.text = it.stats.seasonMatchesPlayed_overall.toString()
            gamesPlayed.layoutParams = TableRow.LayoutParams( 0,TableRow.LayoutParams.WRAP_CONTENT, 1F)
            //wins
            val wins = TextView(this)
            wins.text = it.stats.seasonWinsNum_overall.toString()
            wins.layoutParams = TableRow.LayoutParams( 0,TableRow.LayoutParams.WRAP_CONTENT, 1F)
            //losses
            val losses = TextView(this)
            losses.text = it.stats.seasonLossesNum_overall.toString()
            losses.layoutParams = TableRow.LayoutParams( 0,TableRow.LayoutParams.WRAP_CONTENT, 1F)
            //goals
            val goals = TextView(this)
            val g = "${it.stats.seasonGoals_overall} : ${it.stats.seasonConcededNum_overall}"
            goals.text = g
            goals.setPadding(2,0,1,0)
            goals.layoutParams = TableRow.LayoutParams( 0,TableRow.LayoutParams.WRAP_CONTENT, 4F)
            //points
            val points = TextView(this)
            points.text = (it.stats.seasonWinsNum_overall * 3 + it.stats.seasonDrawsNum_overall).toString()
            points.layoutParams = TableRow.LayoutParams( 0,TableRow.LayoutParams.WRAP_CONTENT, 2F)
            //Add views
            tr.addView(tablePos)
            tr.addView(team)
            tr.addView(gamesPlayed)
            tr.addView(wins)
            tr.addView(losses)
            tr.addView(goals)
            tr.addView(points)
            table.addView(tr, index + 1)
        }
    }

    override fun onBackPressed() {
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
        super.onBackPressed()
    }
}