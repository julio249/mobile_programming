package com.example.final_project.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.final_project.R
import com.example.final_project.models.Gallery
import com.squareup.picasso.Picasso

class GalleryPageActivity: AppCompatActivity(){

    //Declaration of UI components
    private var galleryTitle: TextView? =null
    private var galleryAddress : TextView? =null
    private var galleryCity: TextView?=null
    private var galleryState: TextView?=null
    private var toolbarGalleryDetail: Toolbar?=null
    private var galleryImage: ImageView? =null
    private var btnUrl: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_gallery_detail)

        galleryTitle = findViewById(R.id.tv_title_gallery)
        galleryAddress = findViewById(R.id.tv_gallery_city)
        galleryCity = findViewById(R.id.tv_gallery_city)
        galleryState = findViewById(R.id.tv_gallery_state)
        galleryImage = findViewById(R.id.iv_gallery_image)
        toolbarGalleryDetail = findViewById(R.id.toolBar)

        var galleryDetailModel: Gallery? = null

        if (intent.hasExtra(HomeActivity.EXTRA_GALLERY_DETAILS))
        {
            galleryDetailModel = intent.getParcelableExtra(HomeActivity.EXTRA_GALLERY_DETAILS)
        }

        if (galleryDetailModel!=null)
        {
            setSupportActionBar(toolbarGalleryDetail)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = ""
            toolbarGalleryDetail!!.setNavigationOnClickListener {
                onBackPressed()
            }

            galleryTitle?.text = galleryDetailModel.name
            galleryAddress?.text = galleryDetailModel.address1
            galleryCity?.text = galleryDetailModel.city
            galleryState?.text = galleryDetailModel.state

            Picasso.get().load(galleryDetailModel.image_url).into(galleryImage)

        }

//        btnUrl!!.setOnClickListener {
//
//            var browserIntent = Intent(Intent.ACTION_VIEW)
//            browserIntent.setData(Uri.parse(galleryDetailModel!!.url))
//            startActivity(browserIntent)
//        }



    }

}