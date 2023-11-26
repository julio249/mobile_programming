package com.example.final_project.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.models.Festival
import com.example.final_project.R
import com.squareup.picasso.Picasso

class FestivalAdapter(private val context: Context, private val festivalList: List<Festival>):
    RecyclerView.Adapter<FestivalAdapter.MyViewHolder> () {

    private var onClickListener : OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FestivalAdapter.MyViewHolder {

        val itemViewFestival = LayoutInflater.from(parent.context).inflate(
            com.example.final_project.R.layout.item_festival,
            parent,
            false
        )
        return MyViewHolder(itemViewFestival)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentFestival = festivalList[position]

        holder.eventName.text = currentFestival.eventName
        holder.venue.text = currentFestival.venue
        holder.date.text = currentFestival.date

        Picasso.get().load(currentFestival.imageUrl).into(holder.image)

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
        val eventName: TextView = itemView.findViewById(R.id.tv_title)
        val venue: TextView = itemView.findViewById(R.id.tv_location)
        val date: TextView = itemView.findViewById(R.id.tv_startDate)
        val image: ImageView = itemView.findViewById(R.id.iv_festival_image)
    }
}