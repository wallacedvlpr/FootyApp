package com.example.footyapp.view

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
import kotlinx.android.synthetic.main.clubs_frag_list_layout.*

class FavoritesFragment private constructor(): Fragment() {
    private val adapter by lazy {
        FavoritesAdapter()
    }
    companion object{
        fun newInstance(): FavoritesFragment = FavoritesFragment()
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
        return inflater.inflate(
            R.layout.favorites_frag_list_layout,
            container,
            false)
    }
    fun getDataSet(data: List<ClubItem>){
        adapter.dataSet = data
    }

    private fun initRecyclerView(){
        club_frag_recycler_view.layoutManager = LinearLayoutManager(activity)
        club_frag_recycler_view.adapter = adapter
    }
    inner class FavoritesAdapter :
        RecyclerView.Adapter<FavItemViewHolder>(){
        var dataSet: List<ClubItem> = emptyList()
            set(value) {
                field = value
                notifyDataSetChanged()
            }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavItemViewHolder {
            return FavItemViewHolder(
                LayoutInflater
                    .from(parent.context)
                    .inflate(
                        R.layout.favorites_frag_list_item_layout,
                        parent,
                        false
                    )
            )
        }

        override fun getItemCount(): Int = dataSet.size

        override fun onBindViewHolder(holder: FavItemViewHolder, position: Int) {
            holder.onBind(dataSet[position])
        }

    }//endOf Adapter Class

    inner class FavItemViewHolder(itemView: View):
        RecyclerView.ViewHolder(itemView){

        fun onBind(c: ClubItem){

            itemView.setOnClickListener {
                val newIntent = Intent(activity, DetailedClubActivity::class.java)
                activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                startActivity(newIntent)
            }
        }
    }//endOf ItemViewHolder
}