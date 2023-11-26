package com.example.final_project.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R

class PlaceHolderFeedAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_feed_placeholder, parent, false)
        return PlaceholderViewHolder(view)
    }
    override fun getItemCount(): Int {

        return 10
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    inner class PlaceholderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}