package com.example.footyapp.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.lang.Exception

class ViewPagerAdapter(fragment: FragmentActivity,
                       private val homeFragment: HomeFragment):
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> homeFragment
            else -> throw Exception("Exception in PageAdapter Class, createFragment")
        }
    }
}