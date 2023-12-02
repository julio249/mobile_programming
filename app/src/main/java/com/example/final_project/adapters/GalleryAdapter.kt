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
import com.example.final_project.models.Gallery
import com.squareup.picasso.Picasso
import kotlinx.coroutines.joinAll

class GalleryAdapter(private val context: Context, private val galleryList: List<Gallery>):
    RecyclerView.Adapter<GalleryAdapter.MyViewHolder> (){

    private var onClickListener : GalleryAdapter.OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryAdapter.MyViewHolder {

        val itemViewGallery = LayoutInflater.from(parent.context).inflate(
            com.example.final_project.R.layout.item_gallery,
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
        holder.state.text = currentGallery.state
        if (!currentGallery.image_url.isNullOrEmpty())
        {
            Picasso.get().load(currentGallery.image_url).into(holder.image)

        }

        holder.itemView.setOnClickListener{
            if (onClickListener != null)
            {
                onClickListener!!.onClick(position,currentGallery)
            }
        }
    }

    override fun getItemCount(): Int {

        return galleryList.size
    }
    fun setOnClickListener(onClickListener: OnClickListener){
        this.onClickListener =onClickListener
    }

    interface OnClickListener{
        fun onClick(position: Int,model:Gallery)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val name: TextView = itemView.findViewById(R.id.tv_gallery_title)
        val image: ImageView = itemView.findViewById(R.id.iv_gallery_image)
        val address: TextView = itemView.findViewById(R.id.tv_gallery_address)
        val city: TextView = itemView.findViewById(R.id.tv_gallery_city)
        val state: TextView = itemView.findViewById(R.id.tv_gallery_city)
    }

}
