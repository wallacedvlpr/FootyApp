package com.example.footyapp.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.footyapp.R
import com.example.footyapp.model.League
import com.example.footyapp.view.activities.LeagueActivity
import kotlinx.android.synthetic.main.leagues_frag_list_layout.*

class LeaguesFragment private constructor(): Fragment(){

    private val adapter by lazy {
        LeagueAdapter()
    }

    companion object{
        fun newInstance(): LeaguesFragment =
            LeaguesFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.leagues_frag_list_layout, container, false)
    }

    fun getDataSet(data: List<League>){
        adapter.dataSet = data
    }
    private fun initRecyclerView(){
        leagues_frag_recycler_view.layoutManager = LinearLayoutManager(activity)
        leagues_frag_recycler_view.adapter = adapter
    }

    inner class LeagueAdapter: RecyclerView.Adapter<LeagueItemViewHolder>() {
        var dataSet: List<League> = emptyList()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueItemViewHolder {
            return LeagueItemViewHolder(
                LayoutInflater
                    .from(parent.context)
                    .inflate(
                        R.layout.leagues_frag_list_item_layout,
                        parent,
                        false)
            )
        }
        override fun getItemCount(): Int = dataSet.size

        override fun onBindViewHolder(holder: LeagueItemViewHolder, position: Int) {
            holder.onBind(dataSet[position], position)
        }

    }//endOf Adapter class
    inner class LeagueItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val tvLeagueName: TextView = itemView.findViewById(R.id.tv_league_name_league_item_layout)

        fun onBind(l: League, p: Int){
            tvLeagueName.text = l.name
            itemView.setOnClickListener {
                val newIntent = Intent(activity, LeagueActivity::class.java)
                newIntent.putExtra("League_Id", if (p % 2 == 1) 2012 else 1625)
                activity?.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
                startActivity(newIntent)
            }
        }
    }//endOf CardViewHolder class
}// endOf HomeFragment class
