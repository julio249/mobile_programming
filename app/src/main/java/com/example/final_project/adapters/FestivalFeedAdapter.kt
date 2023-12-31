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

class FestivalFeedAdapter (private val context: Context, private var festivalList: List<Festival>):
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

        holder.name.text = currentFestival.eventName
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
        val name: TextView = itemView.findViewById(R.id.title_festival)
        val venue: TextView = itemView.findViewById(R.id.venue)
        val image: ImageView = itemView.findViewById(R.id.iv_festival_image)
        val date: TextView = itemView.findViewById(R.id.date)

    }

    fun submitList(festivalList: List<Festival>){
        this.festivalList = festivalList
        notifyDataSetChanged()
    }
}