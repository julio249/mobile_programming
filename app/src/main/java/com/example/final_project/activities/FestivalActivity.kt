package com.example.final_project.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.Volley
import com.example.final_project.R
import com.example.final_project.adapters.FestivalFeedAdapter
import com.example.final_project.models.Festival


class FestivalActivity : AppCompatActivity(), FestivalFeedAdapter.OnClickListener {
    //Declares Recycler Views
    private lateinit var festivalFeedRecyclerView: RecyclerView
    //Declares the festival adapter
    private lateinit var festivalFeedAdapter: FestivalFeedAdapter
    private var toolBarFestivalFeed : Toolbar? = null
    private lateinit var festival_search_view: SearchView

    private var city: String = ""
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.festival_activity)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        toolBarFestivalFeed = findViewById(R.id.toolBarFestivalFeed)
        festivalFeedRecyclerView = findViewById(R.id.rv_festival_feed)
        setSupportActionBar(toolBarFestivalFeed)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        festival_search_view =findViewById(R.id.festival_search_view)

        supportActionBar!!.title = "Event"
        toolBarFestivalFeed!!.setNavigationOnClickListener {
            onBackPressed()
        }

        festival_search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.e("Query",query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.e("Query",newText!!)
                return true
            }
        })

        if (intent.hasExtra(HomeActivity.EXTRA_LATITUDE))
        {
            city = intent.getStringExtra(HomeActivity.EXTRA_EVENTS_CITY).toString()
            latitude = intent.getDoubleExtra(HomeActivity.EXTRA_LATITUDE,0.0)
            longitude = intent.getDoubleExtra(HomeActivity.EXTRA_LONGITUDE,0.0)

            Log.e("SetLongLAt", this.latitude.toString())
            Log.e("SetLongLAt", this.longitude.toString())
        }


        festivalActivityApiRequest(city,latitude,longitude)


    }




    override fun onClick(position: Int, model: Festival) {
        TODO("Not yet implemented")
    }

    private fun setupFestivalFeedRecyclerView (festivalList: ArrayList<Festival>)
    {
        festivalFeedRecyclerView.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
//        FestivalFeedAdapter = FestivalFeedAdapter(this,festivalList)
        festivalFeedRecyclerView.adapter = festivalFeedAdapter


    }

    private fun festivalActivityApiRequest(location:String,latitude:Double,longitude:Double)
    {
        Log.e("FestivalLoc",latitude.toString())
        val queue = Volley.newRequestQueue(this) // Create a request queue
        val ticketmasterApiClient = TicketmasterApiClient(resources.getString(R.string.ticket_master_api_key), queue)

        ticketmasterApiClient.searchUpComingMusicEvents(location,latitude,longitude,
            {
                Toast.makeText(this,"Connected to TicketMaster successfully" , Toast.LENGTH_SHORT).show()
                getFestivalList(it)

            },
            {
                Toast.makeText(this,"Api Error" , Toast.LENGTH_SHORT).show()
            })
    }

    private fun getFestivalList(festivals: List<Festival>) {
        try {
//            val dbHandler = DatabaseHandler(this)
//            val getFestivalList: festivals

            if (festivals.size > 0) {
//                message("List Seems to be populated")
//                tv_no_records?.visibility = View.GONE
                festivalFeedRecyclerView.visibility = View.VISIBLE
                setupFestivalRecyclerView(festivals)
            } else {
//                message("List Seems to be not populated")
                festivalFeedRecyclerView?.visibility = View.GONE
//                tv_no_records?.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            e.printStackTrace()
//            message("An error occurred: ${e.message}")
        }
    }

    private fun setupFestivalRecyclerView (festivalList: List<Festival>)
    {
        festivalFeedRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        festivalFeedAdapter = FestivalFeedAdapter(this,festivalList)
        festivalFeedRecyclerView.adapter = festivalFeedAdapter

        //Shows details of the clicked festival

        festivalFeedAdapter.setOnClickListener(object : FestivalFeedAdapter.OnClickListener {
            override fun onClick(position: Int, model: Festival)
            {
                val intent = Intent(this@FestivalActivity, FestivalPageActivity::class.java)
                intent.putExtra(HomeActivity.EXTRA_FESTIVAL_DETAILS,model)
                startActivity(intent)

            }
        })
    }
}


