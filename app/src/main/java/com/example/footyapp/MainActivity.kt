package com.example.footyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.viewpager.widget.PagerAdapter
import com.example.footyapp.view.HomeFragment
import com.example.footyapp.view.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
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
                } else {
                    tv_no_connection.visibility = View.GONE
                }
            })
        }
    }
    private fun init(){
        pager.adapter = ViewPagerAdapter(this, HomeFragment.newInstance())
        TabLayoutMediator(tab_layout, pager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when(position){
                    0-> tab.text = "Home"
                    1-> tab.text = "League"
                    2-> tab.text = "Teams"
                    3-> tab.text = "Stats"
                    else ->   Exception("Error in the TabLayout").printStackTrace()
                }
            }).attach()
    }
}