package com.example.final_project.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.final_project.R
import com.example.final_project.models.Play
import com.squareup.picasso.Picasso

class PlayPageActivity: AppCompatActivity(){

    private var playTitle: TextView? = null
    private var playVenue: TextView? =null
    private var playdate: TextView? = null
    private var iv_play_image: ImageView? = null
    private var toolbarPlayDetail : Toolbar?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_play_detail)

        playTitle = findViewById(R.id.tv_title_play_detail)
        playVenue = findViewById(R.id.tv_location_play_detail)
        playdate = findViewById(R.id.tv_startDate)
        toolbarPlayDetail = findViewById(R.id.toolBar_play)
        iv_play_image = findViewById(R.id.iv_play_image_detail)

        var playDetailModel: Play? =null

        if (intent.hasExtra(HomeActivity.EXTRA_PLAY_DETAILS))
        {
            playDetailModel = intent.getParcelableExtra(HomeActivity.EXTRA_PLAY_DETAILS)

        }

        if (playDetailModel != null)
        {
            setSupportActionBar(toolbarPlayDetail)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = ""

            toolbarPlayDetail!!.setNavigationOnClickListener{
                onBackPressed()
            }

            playTitle?.text = playDetailModel.title
            playVenue?.text = playDetailModel.venue
            playdate?.text = playDetailModel.date

            Picasso.get().load(playDetailModel.imageUrl).into(iv_play_image)

        }




    }



}
