package com.example.final_project.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R
import com.example.final_project.models.Festival
import com.example.final_project.models.Gallery
import com.squareup.picasso.Picasso

class GalleryFeedAdapter(private val context: Context,private var galleryList: List<Gallery>):
    RecyclerView.Adapter<GalleryFeedAdapter.MyViewHolder>(){

    private var onClickListener : GalleryFeedAdapter.OnClickListener? = null



    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val name: TextView = itemView.findViewById(R.id.title_gallery_feed)
        val image: ImageView = itemView.findViewById(R.id.iv_gallery_feed_image)
        val address: TextView = itemView.findViewById(R.id.tv_gallery_feed_address)
        val city: TextView = itemView.findViewById(R.id.tv_feed_gallery_city)
//        val state: TextView = itemView.findViewById(R.id.tv_gallery_city)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryFeedAdapter.MyViewHolder {

        val itemViewGallery = LayoutInflater.from(parent.context).inflate(
            com.example.final_project.R.layout.item_gallery_feed,
            parent,
            false
        )

        return MyViewHolder(itemViewGallery)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentGallery = galleryList[position]

        holder.name.text = currentGallery.name
        holder.address.text = currentGallery.address1
        holder.city.text = currentGallery.city

        if (!currentGallery.image_url.isNullOrEmpty())
        {
            Picasso.get().load(currentGallery.image_url).into(holder.image)

        }

        holder.itemView.setOnClickListener{

            if (onClickListener!=null)
            {
                onClickListener!!.onClick(position,currentGallery)
            }
        }
    }

    override fun getItemCount(): Int {

        return galleryList.size
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener{

        fun onClick(position: Int, model: Gallery)
    }


}