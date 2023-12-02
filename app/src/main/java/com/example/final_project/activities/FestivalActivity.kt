package com.example.final_project.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.TextView
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
import okhttp3.internal.filterList


class FestivalActivity : BaseActivity(), FestivalFeedAdapter.OnClickListener {

    //Declares Recycler Views
    private lateinit var festivalFeedRecyclerView: RecyclerView
    //Declares the festival adapter
    private lateinit var festivalFeedAdapter: FestivalFeedAdapter
    private var toolBarFestivalFeed : Toolbar? = null
    private lateinit var festival_search_view: SearchView

    //Buttons for preferences
    private lateinit var btn_rock: TextView
    private lateinit var btn_pop: TextView
    private lateinit var btn_edm: TextView
    private lateinit var btn_latin: TextView


    //Location variables
    private var city: String = ""
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    //Screen Elements
    private lateinit var tv_no_records : TextView

    private lateinit var festivalList :List<Festival>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.festival_activity)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        showProgressDialog("Please Wait")
        toolBarFestivalFeed = findViewById(R.id.toolBarFestivalFeed)
        festivalFeedRecyclerView = findViewById(R.id.rv_festival_feed)
        setSupportActionBar(toolBarFestivalFeed)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        festival_search_view =findViewById(R.id.festival_search_view)
        tv_no_records = findViewById(R.id.tv_no_records)

        supportActionBar!!.title = "Event"
        toolBarFestivalFeed!!.setNavigationOnClickListener {
            onBackPressed()
        }
        //Initialization of genre buttons
        btn_edm = findViewById(R.id.btn_edm)
        btn_rock = findViewById(R.id.btn_rock)
        btn_pop = findViewById(R.id.btn_pop)
        btn_latin = findViewById(R.id.btn_latin)
        //Finds events based on what the user types on the search bar
        festival_search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                Log.e("Query",query!!)
                filterFestivalList(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.e("Query",newText!!)
                filterFestivalList(newText)

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


        festivalActivityApiRequest(city,latitude,longitude, mode = "default")

        btn_edm.setOnClickListener{
            festivalActivityApiRequest("Electronic",latitude,longitude, mode = "key_word")
        }
        btn_pop.setOnClickListener{
            festivalActivityApiRequest("Pop",latitude,longitude, mode = "key_word")
        }
        btn_rock.setOnClickListener{
            festivalActivityApiRequest("Rock",latitude,longitude, mode = "key_word")
        }
        btn_latin.setOnClickListener {
            festivalActivityApiRequest("Latin",latitude,longitude, mode = "key_word")
        }

    }

    private fun filterFestivalList(query:String?)
    {
        val filteredList = festivalList.filter {
            it.eventName!!.contains(query.orEmpty(),ignoreCase = true)
        }
        festivalFeedAdapter.submitList(filteredList)
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

    private fun festivalActivityApiRequest(word:String,latitude:Double,longitude:Double,mode:String)
    {
        Log.e("FestivalLoc",latitude.toString())
        val queue = Volley.newRequestQueue(this) // Create a request queue
        val ticketmasterApiClient = TicketmasterApiClient(resources.getString(R.string.ticket_master_api_key), queue)

        if (mode=="default")
        {
            ticketmasterApiClient.searchUpComingMusicEvents(word,latitude,longitude,
                {

                    festivalList = it
                    hideProgressDialog()
                    //Toast.makeText(this,"Connected to TicketMaster successfully" , Toast.LENGTH_SHORT).show()
                    getFestivalList(it)

                },
                {

                    showErrorSnackBar("Check your Internet Connection and Location service")
//                    Toast.makeText(this,"Api Error" , Toast.LENGTH_SHORT).show()
                })
        }
        if (mode=="key_word")
        {
            ticketmasterApiClient.searchEventsByKeyword(word,latitude,longitude,
                {
                    hideProgressDialog()
                    festivalList = it
//                    Toast.makeText(this,"Connected to TicketMaster successfully" , Toast.LENGTH_SHORT).show()
                    getFestivalList(it)

                },
                {
                    showErrorSnackBar("Check your Internet Connection and Location service")

//                    Toast.makeText(this,"Api Error" , Toast.LENGTH_SHORT).show()
                })
        }
    }

    private fun getFestivalList(festivals: List<Festival>) {
        try {


            if (festivals.size > 0) {
//                message("List Seems to be populated")
                tv_no_records?.visibility = View.GONE
                festivalFeedRecyclerView.visibility = View.VISIBLE
                setupFestivalRecyclerView(festivals)
            } else {
//                message("List Seems to be not populated")
                festivalFeedRecyclerView?.visibility = View.GONE
                tv_no_records?.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            e.printStackTrace()
//            message("An error occurred: ${e.message}")
        }
    }

    private fun setupFestivalRecyclerView (festivalList: List<Festival>)
    {
        festivalFeedRecyclerView.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,
            false)

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


