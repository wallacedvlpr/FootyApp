package com.example.footyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.footyapp.utils.LiveDataConnection
import com.example.footyapp.utils.InjectorUtils
import com.example.footyapp.view.fragments.FavoritesFragment
import com.example.footyapp.view.fragments.ClubsFragment
import com.example.footyapp.view.fragments.LeaguesFragment
import com.example.footyapp.view.ViewPager.ViewPagerAdapter
import com.example.footyapp.viewmodel.FootyViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    lateinit var leaguesFragment: LeaguesFragment
    lateinit var clubsFragment: ClubsFragment
    lateinit var favoritesFragment: FavoritesFragment
    private val factory by lazy {
        InjectorUtils.provideFootyViewModelFactory(applicationContext)
    }
    private val viewModel by viewModels<FootyViewModel>{
        factory
    }
    private val liveDataConnection by lazy {
        LiveDataConnection.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        leaguesFragment = LeaguesFragment.newInstance()
        clubsFragment = ClubsFragment.newInstance()
        favoritesFragment = FavoritesFragment.newInstance()
        onConnected()
    }

    private fun onConnected(){
        liveDataConnection.registerCallBack()
        liveDataConnection.isConnectionLive().observe(this, Observer{
            showViews(it)
        })
    }

    private fun showViews(bool: Boolean) {
        if(bool){
            tv_no_connection_main.visibility = View.INVISIBLE
            tv_no_connection_main.setTextSize(TypedValue.COMPLEX_UNIT_SP, 0F)
            init()
        }else {
            tv_no_connection_main.visibility = View.VISIBLE
            tv_no_connection_main.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.0F)
        }
    }

    private fun init(){
        getData()
        pager.adapter = ViewPagerAdapter(
            this,
            leaguesFragment,
            clubsFragment,
            favoritesFragment
        )
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

    private fun getData() {
        viewModel.getTeams()
            .observe(this, Observer { TeamsResponse ->
                clubsFragment.getData(TeamsResponse.data)
            })
        viewModel.getLeagues()
            .observe(this, Observer { response ->
                leaguesFragment.getData(response.data)
            })

    }

    override fun onStop() {
        //liveDataConnection.unregisterCallBack()
        super.onStop()
    }
    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}