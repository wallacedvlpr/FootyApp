package com.example.footyapp.view.fragments


import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.footyapp.R
import com.example.footyapp.model.db.Favorite
import com.example.footyapp.view.activities.DetailedClubActivity

class FavItemViewHolder(itemView: View):
    RecyclerView.ViewHolder(itemView){
    private val tv: TextView = itemView.findViewById(R.id.tv_name_fav_frag_list_item)
    fun onBind(favorite: Favorite, fragmentActivity: FragmentActivity?, deleteFavorite:(fav: Favorite) -> Unit){
        tv.text = favorite.favName
        itemView.setOnClickListener {
            fragmentActivity?.let { activity ->
                val newIntent = Intent(activity, DetailedClubActivity::class.java)
                newIntent.putExtra("Club_ID", favorite._id)
                activity.startActivity(newIntent)
            }
        }
        itemView.setOnLongClickListener {
            deleteFavorite.invoke(favorite)
            return@setOnLongClickListener true
        }
    }
}//endOf ItemViewHolder