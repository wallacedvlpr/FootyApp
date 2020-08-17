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
import com.example.footyapp.model.db.Favorites
import com.example.footyapp.view.activities.DetailedClubActivity
import kotlinx.android.synthetic.main.favorites_frag_list_layout.*

class FavoritesFragment private constructor(): Fragment() {
    private val adapter by lazy {
        FavoritesAdapter()
    }
    companion object{
        fun newInstance(): FavoritesFragment =
            FavoritesFragment()
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


    private fun initRecyclerView(){
        fav_frag_recycler_view.layoutManager = LinearLayoutManager(activity)
        fav_frag_recycler_view.adapter = adapter
    }
    inner class FavoritesAdapter :
        RecyclerView.Adapter<FavItemViewHolder>(){
        var dataSet: List<Favorites> = getData()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        fun getData(): List<Favorites>{
            return listOf(
                Favorites(59,"Arsenal FC", 't'),
                Favorites(93, "Manchester City FC", 't'),
                Favorites(149, "Manchester United FC", 't'))
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
        private val tv: TextView = itemView.findViewById(R.id.tv_name_fav_frag_list_item)
        fun onBind(c: Favorites){
            tv.text = c.favName
            itemView.setOnClickListener {
                val newIntent = Intent(activity, DetailedClubActivity::class.java)
                newIntent.putExtra("Club_ID", c._id)
                activity?.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
                startActivity(newIntent)
            }
        }
    }//endOf ItemViewHolder
}