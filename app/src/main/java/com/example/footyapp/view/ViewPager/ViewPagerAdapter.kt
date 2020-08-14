package com.example.footyapp.view.ViewPager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.footyapp.view.fragments.FavoritesFragment
import com.example.footyapp.view.fragments.ClubsFragment
import com.example.footyapp.view.fragments.LeaguesFragment
import java.lang.Exception

class ViewPagerAdapter(fragment: FragmentActivity,
                       private val fragment1: LeaguesFragment,
                       private val fragment2: ClubsFragment,
                       private val fragment3: FavoritesFragment
):
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> fragment1
            1 -> fragment2
            2 -> fragment3
            else -> throw Exception("Exception in PageAdapter Class, createFragment")
        }
    }
}