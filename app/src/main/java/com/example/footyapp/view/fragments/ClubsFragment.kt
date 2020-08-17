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
import com.example.footyapp.model.ClubItem
import com.example.footyapp.view.activities.DetailedClubActivity
import kotlinx.android.synthetic.main.clubs_frag_list_layout.*

class ClubsFragment private constructor(): Fragment(){
    private val adapter by lazy {
        ClubAdapter()
    }
    companion object{
        fun newInstance(): ClubsFragment =
            ClubsFragment()
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
        return inflater.inflate(R.layout.clubs_frag_list_layout,container, false)
    }
    fun getDataSet(data: List<ClubItem>){
        adapter.dataSet = data
    }

    private fun initRecyclerView(){
        club_frag_recycler_view.layoutManager = LinearLayoutManager(activity)
        club_frag_recycler_view.adapter = adapter
    }
    inner class ClubAdapter :
        RecyclerView.Adapter<ClubItemViewHolder>(){
        var dataSet: List<ClubItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubItemViewHolder {
            return ClubItemViewHolder(
                LayoutInflater
                    .from(parent.context)
                    .inflate(
                        R.layout.clubs_frag_list_item_layout,
                        parent,
                        false
                    )
            )
        }

        override fun getItemCount(): Int = dataSet.size

        override fun onBindViewHolder(holder: ClubItemViewHolder, position: Int) {
            holder.onBind(dataSet[position])
        }

    }//endOf Adapter Class

    inner class ClubItemViewHolder(itemView: View):
        RecyclerView.ViewHolder(itemView){
        private val tvClubName: TextView = itemView.findViewById(R.id.club_detail_club_name)
        fun onBind(c: ClubItem){
            tvClubName.text = c.english_name
            itemView.setOnClickListener {
                val newIntent = Intent(activity, DetailedClubActivity::class.java)
                activity?.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
                newIntent.putExtra("Club_ID", c.id)
                startActivity(newIntent)
            }
        }
    }//endOf ItemViewHolder
}