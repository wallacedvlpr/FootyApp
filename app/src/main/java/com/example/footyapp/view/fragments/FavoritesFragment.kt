package com.example.footyapp.view.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.footyapp.R
import com.example.footyapp.utils.InjectorUtils
import com.example.footyapp.viewmodel.FavoritesViewModel
import kotlinx.android.synthetic.main.favorites_frag_list_layout.*

class FavoritesFragment private constructor(): Fragment() {

    private val factory = InjectorUtils.provideFavoritesViewModelFactory()
    private val viewModel by lazy {
        activity?.let {
            ViewModelProvider(it, factory)
                .get(FavoritesViewModel::class.java)
        }
    }

    private val adapter by lazy {
        FavoritesAdapter()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        adapter.activity = activity
        return inflater.inflate(
            R.layout.favorites_frag_list_layout,
            container,
            false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        initRecyclerView()
    }

    private fun getData(){
        fav_frag_recycler_view.layoutManager = LinearLayoutManager(activity)
        viewModel?.getFavorites()?.observe(viewLifecycleOwner, Observer {
            adapter.dataSet  = it
        })
    }

    private fun initRecyclerView(){
        fav_frag_recycler_view.adapter = adapter
    }

    companion object{
        fun newInstance(): FavoritesFragment =
            FavoritesFragment()
    }
}
