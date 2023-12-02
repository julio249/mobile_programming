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
import com.example.final_project.adapters.GalleryAdapter
import com.example.final_project.adapters.GalleryFeedAdapter
import com.example.final_project.models.Festival
import com.example.final_project.models.Gallery

class GalleryActivity: BaseActivity() , View.OnClickListener {

    //Declares Recycler Views
    private lateinit var galleryFeedRecyclerView: RecyclerView
    private lateinit var galleryFeedAdapter: GalleryFeedAdapter
    private var toolBarGalleryFeed: Toolbar? = null
    private lateinit var gallery_search_view: SearchView

    //Location variables
    private var city: String = ""
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    //Screen elements
    private lateinit var tv_no_record:TextView
    private lateinit var galleryList: List<Gallery>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gallery_activity)

        //Sets full screen mode
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        showProgressDialog("Please Wait")

        toolBarGalleryFeed = findViewById(R.id.toolBarGalleryFeed)
        galleryFeedRecyclerView = findViewById(R.id.rv_gallery_feed)
        setSupportActionBar(toolBarGalleryFeed)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        gallery_search_view = findViewById(R.id.gallery_search_view)
        tv_no_record = findViewById(R.id.tv_no_gallery)

        supportActionBar!!.title = "Gallery"

        toolBarGalleryFeed!!.setNavigationOnClickListener {
            onBackPressed()
        }

        if (intent.hasExtra(HomeActivity.EXTRA_LATITUDE))
        {
            city = intent.getStringExtra(HomeActivity.EXTRA_EVENTS_CITY).toString()
            latitude = intent.getDoubleExtra(HomeActivity.EXTRA_LATITUDE,0.0)
            longitude = intent.getDoubleExtra(HomeActivity.EXTRA_LONGITUDE,0.0)

            Log.e("SetLongLAt", this.latitude.toString())
            Log.e("SetLongLAt", this.longitude.toString())
        }

        galleryActivityApiRequest(city,latitude,longitude, mode = "default")

    }

    private fun galleryActivityApiRequest(word: String, latitude: Double, longitude: Double, mode: String) {

        val queue = Volley.newRequestQueue(this) // Create a request queue
        val yelpApiClient = YelpApiClient(resources.getString(R.string.yelp_api_key), queue)

        yelpApiClient.searchUpComingArtEvents(location="default",latitude,longitude,
            {

                hideProgressDialog()
                Log.e("YelpBusiness",it.toString())
                getGalleryList(it)

            },{
                Toast.makeText(this,"Api Error" , Toast.LENGTH_SHORT).show()
            })

    }

    private fun getGalleryList(galleries: List<Gallery>) {
        runOnUiThread {
            try {
                if (galleries.size > 0) {
                    galleryFeedRecyclerView.visibility = View.VISIBLE
                    setupGalleryRecyclerView(galleries)
                } else {
                    galleryFeedRecyclerView.visibility = View.GONE
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun setupGalleryRecyclerView(galleryList: List<Gallery>) {

        galleryFeedRecyclerView.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)

        galleryFeedAdapter = GalleryFeedAdapter(this,galleryList)

        galleryFeedRecyclerView.adapter = galleryFeedAdapter

        galleryFeedAdapter.setOnClickListener(object :GalleryFeedAdapter.OnClickListener{
            override fun onClick(position: Int, model: Gallery) {

                Log.e("gallery_clicked","Gallery")
                val intent = Intent(this@GalleryActivity, GalleryPageActivity::class.java)
                intent.putExtra(HomeActivity.EXTRA_GALLERY_DETAILS,model)
                startActivity(intent)
            }
        })
    }


    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}