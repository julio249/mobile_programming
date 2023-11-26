package com.example.final_project.adapters

import android.content.Context
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R
import com.example.final_project.models.News
import com.squareup.picasso.Picasso

class NewsAdapter (private val context: Context, private val newsList: List<News>):
    RecyclerView.Adapter<NewsAdapter.MyViewHolder> () {

    private var onClickListener : OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.MyViewHolder {

        val itemViewNews = LayoutInflater.from(parent.context).inflate(
            com.example.final_project.R.layout.item_news,
            parent,
            false
        )
        return MyViewHolder(itemViewNews)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentNews = newsList[position]

        holder.eventName.text = currentNews.title
        holder.content.text = currentNews.brief
//        holder.imageUrl.text = currentFestival.image

        Picasso.get().load(currentNews.image).into(holder.image)

        holder.itemView.setOnClickListener{
            if (onClickListener != null)
            {
                Log.e("Festival Clicked","Festival Clicked")
                onClickListener!!.onClick(position,currentNews)
            }
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener{

        fun onClick(position: Int, model: News)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val eventName: TextView = itemView.findViewById(R.id.tv_news_title)
        val content: TextView= itemView.findViewById(R.id.tv_brief_content)
        val image: ImageView = itemView.findViewById(R.id.iv_news_image)

    }
}