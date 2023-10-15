package com.example.final_project.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.database.DatabaseHandler
import com.example.final_project.models.Festival
import com.example.final_project.database.FestivalRepository
import com.example.final_project.R
import com.example.final_project.adapters.FestivalAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
//    private var eventViewModel : EventViewModel? = null

    private lateinit var festivalsRecyclerView: RecyclerView
    private lateinit var expositionsRecyclerView: RecyclerView
    private lateinit var playsRecyclerView: RecyclerView
    private lateinit var  bottomNavigationView : BottomNavigationView
    private lateinit var myFestivalAdapter: FestivalAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page_activity)

        //Setting Recycler Views for Festivals, plays and expositions.

        festivalsRecyclerView = findViewById(R.id.rv_upcoming_festivals)
        expositionsRecyclerView = findViewById(R.id.rv_upcoming_expositions)
        playsRecyclerView = findViewById(R.id.rv_upcoming_plays)
        bottomNavigationView = findViewById(R.id.bottom_navigation)
//        val repository = FestivalRepository(this)
//        repository.populateFestivalTable()
        getFestivalListFromLocalDB()

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home_btn -> {
                    // Handle item 1 selection
                    // You can put your code here
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

    private fun setupFestivalRecyclerView (festivalList: ArrayList<Festival>)
    {
        festivalsRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true)

        myFestivalAdapter = FestivalAdapter(this,festivalList)
        festivalsRecyclerView.adapter = myFestivalAdapter
        myFestivalAdapter.setOnClickListener(object : FestivalAdapter.OnClickListener {
            override fun onClick(position: Int, model: Festival)
            {
                val intent = Intent(this@HomeActivity, FestivalPageActivity::class.java)
                intent.putExtra(EXTRA_FESTIVAL_DETAILS,model)
                startActivity(intent)

            }
        })
    }

    private fun getFestivalListFromLocalDB() {
        try {
            val dbHandler = DatabaseHandler(this)
            val getFestivalList: ArrayList<Festival> = dbHandler.getFestivalsList()

            if (getFestivalList.size > 0) {
//                message("List Seems to be populated")
//                tv_no_records?.visibility = View.GONE
                festivalsRecyclerView.visibility = View.VISIBLE
                setupFestivalRecyclerView(getFestivalList)
            } else {
//                message("List Seems to be not populated")
                festivalsRecyclerView?.visibility = View.GONE
//                tv_no_records?.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            e.printStackTrace()
//            message("An error occurred: ${e.message}")
        }
    }

    companion object{
        var EXTRA_FESTIVAL_DETAILS = "extra_place_details"
    }
}

