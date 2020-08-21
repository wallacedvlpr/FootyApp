package com.example.footyapp.view.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.footyapp.R
import com.example.footyapp.utils.InjectorUtils
import com.example.footyapp.viewmodel.FootyViewModel
import kotlinx.android.synthetic.main.main_fragment_list_layout.*

class LeaguesFragment private constructor(): Fragment(){

    private val adapter by lazy {
        LeagueAdapter()
    }
    private val factory = InjectorUtils.provideFootyViewModelFactory()
    private val viewModel by viewModels<FootyViewModel>{
        factory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        adapter.activity = activity
        return inflater.inflate(R.layout.main_fragment_list_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        initViews()
    }

    private fun getData(){
        viewModel.getLeagues()
            .observe(viewLifecycleOwner, Observer { response ->
                Log.d("LiveData", response.data[0].name)
                adapter.dataSet = response.data
            })
    }
    private fun initViews(){
        val title = "Leagues"
        main_frag_list_title.text = title
        main_frag_recycler_view.layoutManager = LinearLayoutManager(activity)
        main_frag_recycler_view.adapter = adapter
    }

    companion object{
        fun newInstance(): LeaguesFragment =
            LeaguesFragment()
    }
}// endOf HomeFragment class
