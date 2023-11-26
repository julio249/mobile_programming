package com.example.final_project.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R
import com.example.final_project.models.Play
import com.squareup.picasso.Picasso

class PlayFeedAdapter ( private val context: Context, private val playList: List<Play>):
    RecyclerView.Adapter<PlayFeedAdapter.MyViewHolder> (){

    private var onClickListener : OnClickListener? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlayFeedAdapter.MyViewHolder {

        val itemViewPlay = LayoutInflater.from(parent.context).inflate(
            com.example.final_project.R.layout.item_play_feed,
            parent,
            false
        )
        return PlayFeedAdapter.MyViewHolder(itemViewPlay)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentPlay = playList[position]

        holder.title.text = currentPlay.title
        holder.venue.text = currentPlay.venue
        holder.date.text = currentPlay.date

        Picasso.get().load(currentPlay.imageUrl).into(holder.image)

        holder.itemView.setOnClickListener{
            if (onClickListener != null)
            {
                Log.e("Festival Clicked","Play Clicked")
                onClickListener!!.onClick(position,currentPlay)
            }
        }

    }

    override fun getItemCount(): Int {

        return playList.size
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener{
        fun onClick(position: Int, model: Play)
    }

    class MyViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){

        val title: TextView = itemView.findViewById(R.id.title_play)
        val venue: TextView = itemView.findViewById(R.id.play_venue)
        val image: ImageView = itemView.findViewById(R.id.iv_play_image)
        val date:TextView = itemView.findViewById(R.id.play_date)

    }

}

