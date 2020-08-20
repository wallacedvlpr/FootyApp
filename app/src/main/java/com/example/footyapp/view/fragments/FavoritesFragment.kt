package com.example.footyapp.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.footyapp.R
import com.example.footyapp.model.db.Favorite
import com.example.footyapp.utils.InjectorUtils
import com.example.footyapp.view.activities.DetailedClubActivity
import com.example.footyapp.viewmodel.FavoritesViewModel
import kotlinx.android.synthetic.main.favorites_frag_list_layout.*

class FavoritesFragment private constructor(): Fragment() {
    private val factory = InjectorUtils.provideFavoritesViewModelFactory()
    val viewModel by lazy {
        activity?.let {
            ViewModelProvider(it, factory)
                .get(FavoritesViewModel::class.java)
        }
    }

    private val adapter by lazy {
        FavoritesAdapter()
    }
    companion object{
        fun newInstance(): FavoritesFragment =
            FavoritesFragment()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initRecyclerView()
    }
    private fun initViewModel(){
        viewModel?.getFavorites()?.observe(viewLifecycleOwner, Observer { faves ->
            faves.forEach{
                Log.d("OBSERVER~~", it.toString())
            }
        })
    }
    private fun initRecyclerView(){
        fav_frag_recycler_view.layoutManager = LinearLayoutManager(activity)
        fav_frag_recycler_view.adapter = adapter
    }
    inner class FavoritesAdapter :
        RecyclerView.Adapter<FavItemViewHolder>(){
        var dataSet: List<Favorite> = getData()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        private fun getData(): List<Favorite>{
            return listOf(
                Favorite(59,"Arsenal FC", 't'),
                Favorite(93, "Manchester City FC", 't'),
                Favorite(149, "Manchester United FC", 't'))
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
        fun onBind(c: Favorite){
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