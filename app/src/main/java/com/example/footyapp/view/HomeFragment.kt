package com.example.footyapp.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.footyapp.R

class HomeFragment(): Fragment(){
    /*
    private val adapter by lazy {
        CardAdapter()
    }

    inner class CardAdapter(/* val listener: (item: Card) -> Unit)*/): RecyclerView.Adapter<CardViewHolder>() {

        var dataSet: MutableList<String> = mutableListOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
            TODO("OnCreateViewHolder method")
        }

        override fun getItemCount(): Int {
           dataSet.
        }

        override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
            holder.onBind(position)
        }
    }//endOf CardAdapter class
    inner class CardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        //TODO declare property values in the View HOLDER
        fun onBind(/*listener: (item: Card) -> Unit),*/ position: Int){
            //TODO Implement onBind Function
        }
    }//endOf CardViewHolder class
     */

    override fun onAttach(context: Context) {
        super.onAttach(context)
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
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    fun getDataSet(results: MutableList<String>){
        //adapter.dataSet = results
    }

    private fun initRecyclerView(){
        //recycler_view.layoutManager = LinearLayoutManager(activity)
        //recycler_view.adapter = adapter
    }
    companion object{
        fun newInstance(): HomeFragment = HomeFragment()
    }
}// endOf HomeFragment class