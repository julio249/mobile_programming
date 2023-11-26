package com.example.final_project.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.Volley
import com.example.final_project.R
import com.example.final_project.adapters.PlayFeedAdapter
import com.example.final_project.models.Play

class PlayActivity: AppCompatActivity(), PlayFeedAdapter.OnClickListener {

    //UI elements for item_play_details
    private var tv_title_play_detail: TextView? =null
    private var tv_location_play_detail: TextView? =null
    private var toolBar_play: Toolbar?=null
    private var iv_play_image_detail: ImageView? =null

    //Declaration of Adapters and Recycler Views
    private lateinit var playFeedRecyclerView: RecyclerView
    private lateinit var playFeedAdapter: PlayFeedAdapter

    //Location Variables
    private var city: String = ""
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.play_activity)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        playFeedRecyclerView = findViewById(R.id.rv_play_feed)
        playFeedRecyclerView
        toolBar_play = findViewById(R.id.toolBarPLay)
        setSupportActionBar(toolBar_play)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Play"

        toolBar_play!!.setNavigationOnClickListener { onBackPressed() }

        if (intent.hasExtra(HomeActivity.EXTRA_LATITUDE))
        {
            city = intent.getStringExtra(HomeActivity.EXTRA_EVENTS_CITY).toString()
            latitude = intent.getDoubleExtra(HomeActivity.EXTRA_LATITUDE,0.0)
            longitude = intent.getDoubleExtra(HomeActivity.EXTRA_LONGITUDE,0.0)

            Log.e("SetLongLAt", this.latitude.toString())
            Log.e("SetLongLAt", this.longitude.toString())
        }

        playActivityApiRequest(city,latitude,longitude)

    }

    override fun onClick(position: Int, model: Play) {
        // TODO: implement

    }

    private fun playActivityApiRequest(location:String,latitude:Double,longitude:Double)
    {
        val queue = Volley.newRequestQueue(this)
        val ticketmasterApiClient = TicketmasterApiClient(resources.getString(R.string.ticket_master_api_key), queue)

        ticketmasterApiClient.searchUpComingArtEvents(location,latitude,longitude,
            {
                Toast.makeText(this,"Connected to TicketMaster successfully" , Toast.LENGTH_SHORT).show()
                getPlayList(it)

            },
            {
                Toast.makeText(this,"Api Error" , Toast.LENGTH_SHORT).show()
            })
    }

    private fun getPlayList (plays: List<Play>)
    {
        try {
//            val dbHandler = DatabaseHandler(this)
//            val getFestivalList: festivals

            if (plays.size > 0) {
//                message("List Seems to be populated")
//                tv_no_records?.visibility = View.GONE
                playFeedRecyclerView.visibility = View.VISIBLE
                setupPlayRecyclerView(plays)
            } else {
//                message("List Seems to be not populated")
                playFeedRecyclerView?.visibility = View.GONE
//                tv_no_records?.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            e.printStackTrace()
//            message("An error occurred: ${e.message}")
        }
    }

    private fun setupPlayRecyclerView(playList: List<Play>)
    {
        playFeedRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        playFeedAdapter = PlayFeedAdapter(this,playList)
        playFeedRecyclerView.adapter = playFeedAdapter

        playFeedAdapter.setOnClickListener(object : PlayFeedAdapter.OnClickListener{

            override fun onClick(position: Int, model: Play) {

                val intent = Intent(this@PlayActivity, PlayPageActivity::class.java)
                intent.putExtra(HomeActivity.EXTRA_PLAY_DETAILS,model)
                startActivity(intent)
            }
        })

    }



}
