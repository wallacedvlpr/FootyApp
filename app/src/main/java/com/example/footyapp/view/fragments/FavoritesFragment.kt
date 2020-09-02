package com.example.footyapp.view.fragments


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.footyapp.MainActivity
import com.example.footyapp.R
import com.example.footyapp.model.db.Favorite
import com.example.footyapp.view.IListener
import kotlinx.android.synthetic.main.favorites_frag_list_layout.*

class FavoritesFragment:
    Fragment() {

    private val adapter by lazy {
        FavoritesAdapter()
    }
    private var listener: IListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity){
            listener = context
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        adapter.activity = activity
        adapter.setListener(listener)
        return inflater.inflate(
            R.layout.favorites_frag_list_layout,
            container,
            false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    fun setData(list: List<Favorite>){
        adapter.setData(list)
    }

    private fun initRecyclerView(){
        fav_frag_recycler_view.layoutManager = LinearLayoutManager(activity)
        fav_frag_recycler_view.adapter = adapter
    }

    companion object{
        fun newInstance(): FavoritesFragment =
            FavoritesFragment()
    }
}
