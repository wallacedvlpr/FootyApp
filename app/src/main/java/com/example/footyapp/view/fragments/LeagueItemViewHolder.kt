package com.example.footyapp.view.fragments

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.footyapp.R
import com.example.footyapp.model.League
import com.example.footyapp.view.activities.LeagueActivity

class LeagueItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    private val tvLeagueName: TextView
            = itemView.findViewById(R.id.tv_league_name_league_item_layout)
    fun onBind(l: League, p: Int, fragmentActivity: FragmentActivity?){
        tvLeagueName.text = l.name
        itemView.setOnClickListener {
            fragmentActivity?.let { activity ->
                val newIntent = Intent(activity, LeagueActivity::class.java)
                newIntent.putExtra("League_Id", if (p % 2 == 1) 2012 else 1625)
                //fragmentActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                activity.startActivity(newIntent)
            }
        }
    }

}//endOf CardViewHolder class