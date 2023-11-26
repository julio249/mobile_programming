package com.example.final_project.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R

class PlaceholderAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_placeholder, parent, false)
        return PlaceholderViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // No need to bind data for a placeholder
    }

    override fun getItemCount(): Int {
        // Set a reasonable placeholder count
        return 10
    }

    inner class PlaceholderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
