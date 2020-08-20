package com.example.footyapp.view.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.footyapp.R
import com.example.footyapp.model.ClubItem
import kotlinx.android.synthetic.main.clubs_frag_list_layout.*

class ClubsFragment private constructor(): Fragment(){
    private val adapter by lazy {
        ClubAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        adapter.activity = activity
        return inflater.inflate(R.layout.clubs_frag_list_layout,container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }
    fun getDataSet(data: List<ClubItem>){
        adapter.dataSet = data
    }

    private fun initRecyclerView(){
        club_frag_recycler_view.layoutManager = LinearLayoutManager(activity)
        club_frag_recycler_view.adapter = adapter
    }

    companion object{
        fun newInstance(): ClubsFragment =
            ClubsFragment()
    }
}