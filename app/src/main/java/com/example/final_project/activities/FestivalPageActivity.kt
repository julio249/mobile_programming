package com.example.final_project.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ButtonBarLayout
import androidx.appcompat.widget.Toolbar
import com.example.final_project.R
import com.example.final_project.models.Festival
import com.squareup.picasso.Picasso


class FestivalPageActivity: AppCompatActivity() {

    //Declaration of UI components
    private var festivalTitle: TextView? =null
    private var festivalLocation: TextView? =null
    private var festivalStartDate: TextView? =null
    private var festivalEndDate: TextView? =null
    private var toolbarFestivalDetail: Toolbar?=null
    private var iv_festival_image: ImageView? =null
    private var btn_url: Button? = null
    private var btn_lineup:Button? = null
    private var btn_spotify_festival: Button? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_festival_detail)

        festivalTitle = findViewById(R.id.tv_title)
        festivalLocation = findViewById(R.id.tv_location)
        festivalStartDate = findViewById(R.id.tv_startDate)
        festivalEndDate = findViewById(R.id.tv_endDate)
        toolbarFestivalDetail = findViewById(R.id.toolBar)
        iv_festival_image = findViewById(R.id.iv_festival_image)
        btn_lineup = findViewById(R.id.btn_lineup_festival)
//        btn_spotify_festival = findViewById(R.id.btn_spotify_festival)
        btn_url = findViewById(R.id.btn_buy_tickets_festival)
        var festivalDetailModel : Festival? = null

        if (intent.hasExtra(HomeActivity.EXTRA_FESTIVAL_DETAILS))
        {
            festivalDetailModel = intent.getParcelableExtra(HomeActivity.EXTRA_FESTIVAL_DETAILS) // Use the same key

        }
        if (festivalDetailModel !=null){

            setSupportActionBar(toolbarFestivalDetail)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = ""
            toolbarFestivalDetail!!.setNavigationOnClickListener{
                onBackPressed()
            }

            festivalTitle?.text = festivalDetailModel.eventName
            festivalLocation?.text = festivalDetailModel.venue
            festivalStartDate?.text = festivalDetailModel.date

            Picasso.get().load(festivalDetailModel.imageUrl).into(iv_festival_image)

        }

        btn_url!!.setOnClickListener{

            var browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.setData(Uri.parse(festivalDetailModel!!.ticketUrl))
            startActivity(browserIntent)
        }

        btn_lineup!!.setOnClickListener {

            var lineupIntent = Intent(this@FestivalPageActivity,LineupActivity::class.java)
            lineupIntent.putExtra(LINEUP_INFO,festivalDetailModel)
            startActivity(lineupIntent)
        }
//        btn_spotify_festival!!.setOnClickListener {
//
//            var browserIntent = Intent(Intent.ACTION_VIEW)
//            browserIntent.setData(Uri.parse(festivalDetailModel!!.lineup[0].spotifyProfile))
//            startActivity(browserIntent)
//        }




    }
    companion object{

        var LINEUP_INFO = "lineup_info"
    }
}
