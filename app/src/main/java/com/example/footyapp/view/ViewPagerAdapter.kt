package com.example.footyapp.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.lang.Exception

class ViewPagerAdapter(fragment: FragmentActivity):
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position){
            in 0 until 3 -> HomeFragment.newInstance()
            else -> throw Exception("Exception in PageAdapter Class, createFragment")
        }
    }
}