package com.example.footyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.footyapp.model.network.NetworkConnection
import com.example.footyapp.view.fragments.FavoritesFragment
import com.example.footyapp.view.fragments.ClubsFragment
import com.example.footyapp.view.fragments.LeaguesFragment
import com.example.footyapp.view.ViewPager.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    private val leaguesFragment = LeaguesFragment.newInstance()
    private val clubsFragment = ClubsFragment.newInstance()
    private val favoritesFragment = FavoritesFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //network check
        val networkConnection = NetworkConnection.getInstance(applicationContext)
        networkConnection?.testConnection(this,
            tv_no_connection_main
        ) { init() }
    }

    private fun init(){
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

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}