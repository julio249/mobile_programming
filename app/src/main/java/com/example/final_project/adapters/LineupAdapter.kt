package com.example.final_project.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R
import com.example.final_project.models.Artist
import com.example.final_project.models.Festival
import com.squareup.picasso.Picasso

class LineupAdapter (private val context: Context, private val artistList: List<Artist>):RecyclerView.Adapter<LineupAdapter.MyViewHolder>(){


    private var onClickListener: OnClickListener? = null

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val artistName: TextView = itemView.findViewById(R.id.tv_artist_name)
        val artistImage: ImageView = itemView.findViewById(R.id.iv_artist_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemViewLineup = LayoutInflater.from(parent.context).inflate(
            com.example.final_project.R.layout.item_lineup,
            parent,
            false
        )

        return MyViewHolder(itemViewLineup)

    }

    interface OnClickListener{

        fun onClick(position: Int, model: Artist)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    override fun getItemCount(): Int {
        return artistList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentArtist = artistList[position]

        holder.artistName.text = currentArtist.name

        Picasso.get().load(currentArtist.image).into(holder.artistImage)


    }
}

