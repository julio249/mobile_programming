package com.example.final_project.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.models.Festival
import com.example.final_project.R

class FestivalFeedAdapter (private val context: Context, private val festivalList: ArrayList<Festival>):
    RecyclerView.Adapter<FestivalFeedAdapter.MyViewHolder> () {

    private var onClickListener : OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FestivalFeedAdapter.MyViewHolder {

        val itemViewFestival = LayoutInflater.from(parent.context).inflate(
            com.example.final_project.R.layout.item_festival_feed,
            parent,
            false
        )
        return MyViewHolder(itemViewFestival)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentFestival = festivalList[position]

        holder.title.text = currentFestival.title
        holder.location.text = currentFestival.location
        holder.startDate.text = currentFestival.startDate


        holder.itemView.setOnClickListener{
            if (onClickListener != null)
            {
                Log.e("Festival Clicked","Festival Clicked")
                onClickListener!!.onClick(position,currentFestival)
            }
        }
    }

    override fun getItemCount(): Int {
        return festivalList.size
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener{

        fun onClick(position: Int, model: Festival)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val title: TextView = itemView.findViewById(R.id.tv_title)
        val location: TextView = itemView.findViewById(R.id.tv_location)
        val startDate: TextView = itemView.findViewById(R.id.tv_startDate)
    }
}