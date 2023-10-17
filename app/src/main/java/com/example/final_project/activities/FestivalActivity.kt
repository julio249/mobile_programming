package com.example.final_project.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R
import com.example.final_project.adapters.FestivalAdapter
import com.example.final_project.adapters.FestivalFeedAdapter
import com.example.final_project.database.DatabaseHandler
import com.example.final_project.models.Festival
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback



class FestivalActivity : AppCompatActivity(), FestivalFeedAdapter.OnClickListener {
    private lateinit var festivalFeedRecyclerView: RecyclerView
    private lateinit var FestivalFeedAdapter: FestivalFeedAdapter
    private var toolBarFestivalFeed : Toolbar? = null
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.festival_activity)
        toolBarFestivalFeed = findViewById(R.id.toolBarFestivalFeed)
        festivalFeedRecyclerView = findViewById(R.id.rv_festival_feed)
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        setSupportActionBar(toolBarFestivalFeed)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Event"
        toolBarFestivalFeed!!.setNavigationOnClickListener {
            onBackPressed()
        }
        getFestivalListFromLocalDB()
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home_btn -> {
                    // Handle item 1 selection
                    // You can put your code here
                    val festivalIntent = Intent(this, HomeActivity::class.java)
                    startActivity(festivalIntent)
                    true // Return true to indicate that the item click event was handled

                }
                R.id.festivals_btn -> {
                    // Handle item 2 selection

//                    val intentEvent = Intent(this,FestivalActivity::class.java)
                    // You can put your code here

                    val festivalIntent = Intent(this, FestivalActivity::class.java)
                    startActivity(festivalIntent)

                    true // Return true to indicate that the item click event was handled
                }
                R.id.galleries_btn -> {
                    // Handle item 2 selection
                    // You can put your code here
                    val galleryIntent = Intent(this, GalleryActivity::class.java)
                    startActivity(galleryIntent)
                    true // Return true to indicate that the item click event was handled
                }
                // Add more cases for other items as needed
                else -> false // Return false if the item click event was not handled
            }
        }


    }


    override fun onClick(position: Int, model: Festival) {
        TODO("Not yet implemented")
    }

    private fun setupFestivalFeedRecyclerView (festivalList: ArrayList<Festival>)
    {
        festivalFeedRecyclerView.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        FestivalFeedAdapter = FestivalFeedAdapter(this,festivalList)
        festivalFeedRecyclerView.adapter = FestivalFeedAdapter


    }

    private fun getFestivalListFromLocalDB() {
        try {
            val dbHandler = DatabaseHandler(this)
            val getFestivalList: ArrayList<Festival> = dbHandler.getFestivalsList()

            if (getFestivalList.size > 0) {
//                message("List Seems to be populated")
//                tv_no_records?.visibility = View.GONE
                festivalFeedRecyclerView.visibility = View.VISIBLE
                setupFestivalFeedRecyclerView(getFestivalList)
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
}


