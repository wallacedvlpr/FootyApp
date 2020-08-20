package com.example.footyapp.view.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.footyapp.R
import com.example.footyapp.model.League
import kotlinx.android.synthetic.main.leagues_frag_list_layout.*

class LeaguesFragment private constructor(): Fragment(){

    private val adapter by lazy {
        activity?.let { LeagueAdapter(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.leagues_frag_list_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    fun getDataSet(data: List<League>){
        adapter?.dataSet = data
    }

    private fun initRecyclerView(){
        leagues_frag_recycler_view.layoutManager = LinearLayoutManager(activity)
        leagues_frag_recycler_view.adapter = adapter
    }

    companion object{
        fun newInstance(): LeaguesFragment =
            LeaguesFragment()
    }
}// endOf HomeFragment class
