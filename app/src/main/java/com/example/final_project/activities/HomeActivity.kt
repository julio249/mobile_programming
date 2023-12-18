package com.example.final_project.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.Volley
import com.example.final_project.models.Festival
import com.example.final_project.R
import com.example.final_project.adapters.FestivalAdapter
import com.example.final_project.adapters.GalleryAdapter
import com.example.final_project.adapters.PlaceholderAdapter
import com.example.final_project.adapters.PlayAdapter
import com.example.final_project.models.Gallery
import com.example.final_project.models.Play
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

import kotlin.math.log

class HomeActivity : BaseActivity(),View.OnClickListener,NavigationView.OnNavigationItemSelectedListener {

    //Recycler views
    private lateinit var festivalsRecyclerView: RecyclerView
    private lateinit var playsRecyclerView: RecyclerView
    private lateinit var galleriesRecyclerView:RecyclerView

    //Adapters
    private lateinit var myFestivalAdapter: FestivalAdapter
    private lateinit var myPlayAdapter:PlayAdapter
    private lateinit var mGalleryAdapter: GalleryAdapter

    //Location
    private lateinit var mfusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var et_location: AppCompatEditText
    private var city: String = ""

    //Date Picker
    private var et_date: AppCompatEditText? = null
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    private var calender = Calendar.getInstance()

    //Buttons
    private var btnFestival: ImageButton? = null
    private var btnGallery: ImageButton? = null
    private var btnMuseum: ImageButton? = null
    private var btnPlays: ImageButton? = null

    //Tool bars
    private lateinit var toolbar_activity: Toolbar
    private var longitude: Double = 0.0
    private var latitude: Double = 0.0


    private var drawer_layout: DrawerLayout? =null


    private fun setLongLat (long: Double, latitude: Double)
    {
        this.latitude = long
        this.longitude = latitude

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        showProgressDialog("Loading")

        if (!Places.isInitialized())
        {
            Places.initialize(this@HomeActivity, resources.getString(R.string.google_place_api_key));
        }
        //Action bar setting
        setUpActionBar()



        //Buttons initialization
        btnFestival = findViewById(R.id.btn_festival)
        btnGallery = findViewById(R.id.btn_gallery)
        btnMuseum = findViewById(R.id.btn_museum)
        btnPlays = findViewById(R.id.btn_plays)

        //Recycler views initialization
        festivalsRecyclerView = findViewById(R.id.rv_upcoming_festivals)
        playsRecyclerView = findViewById(R.id.rv_upcoming_plays)
        galleriesRecyclerView = findViewById(R.id.rv_upcoming_galleries)

        //Initialization of date
        et_date = findViewById(R.id.et_date)

        drawer_layout = findViewById(R.id.drawer_layout)



        //Initialization of location service provider
        mfusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        //Set up location request parameters
        locationRequest = LocationRequest.create().apply {

            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        // Formats the DatePickerListener
        dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calender.set(Calendar.YEAR, year)
            calender.set(Calendar.MONTH, month)
            calender.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            // Updates the date display to include the selected date
            updateDateInView()
        }

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {

            requestLocationUpdates()
            Log.e("Long",it.toString())
        }

        task.addOnFailureListener {e->

            if (e is ResolvableApiException)
            {
                try {
                    e.startResolutionForResult(this@HomeActivity, REQUEST_CHECK_SETTINGS)
                }catch (sendX:IntentSender.SendIntentException){

                }
            }
        }

        et_location = findViewById(R.id.et_location)
        et_location?.setOnClickListener(this)


        setupDefaultRecyclerViews()

        // TODO: Fix UarkNwes api

        val uark = UarkNewsApiClient(this)

        uark.searchNews(
            keyWords = "concert",
            onSuccess = { response ->
                // Handle the successful response
                Toast.makeText(this,"Connected to UARK successfully" , Toast.LENGTH_SHORT).show()
            },
            onError = { errorMessage ->
                // Handle the error
                Toast.makeText(this,"Error Connecting to uark" , Toast.LENGTH_SHORT).show()
            }
        )


        btnFestival?.setOnClickListener {

            val festivalIntent = Intent(this, FestivalActivity::class.java)
            festivalIntent.putExtra(EXTRA_LATITUDE, latitude)
            festivalIntent.putExtra(EXTRA_LONGITUDE, longitude)
            startActivity(festivalIntent)

        }

        btnPlays?.setOnClickListener {
            val playsIntent = Intent(this, PlayActivity::class.java)
            playsIntent.putExtra(EXTRA_LATITUDE, latitude)
            playsIntent.putExtra(EXTRA_LONGITUDE, longitude)
            startActivity(playsIntent)
        }

        btnGallery?.setOnClickListener {

            val galleryIntent = Intent(this,GalleryActivity::class.java)
            galleryIntent.putExtra(EXTRA_LATITUDE,latitude)
            galleryIntent.putExtra(EXTRA_LONGITUDE,longitude)
            startActivity(galleryIntent)
        }

        et_date?.setOnClickListener {

            DatePickerDialog(
                this@HomeActivity,
                dateSetListener,
                calender.get(Calendar.YEAR),
                calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH)
            ).show()
        }


    }

    private fun toggleDrawer()
    {
        if (drawer_layout!!.isDrawerOpen(GravityCompat.START))
        {
            drawer_layout!!.closeDrawer(GravityCompat.START)
        }
        else
        {
            drawer_layout!!.openDrawer(GravityCompat.START)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (drawer_layout!!.isDrawerOpen(GravityCompat.START))
        {
            drawer_layout!!.closeDrawer(GravityCompat.START)
        }
        else{
            doubleBackToExit()
        }
    }
    private fun updateDateInView() {
        val dateFormat = "dd/MM/YYYY"
        val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())
        et_date?.setText(sdf.format(calender.time))
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId){

            R.id.nav_my_profile ->{

//                startActivityForResult(Intent(this,MyProfileActivity::class.java),
//                    MY_PROFILE_REQUEST_CODE)

//                Toast.makeText(this@MainActivity,"My Profile", Toast.LENGTH_SHORT).show()
            }

            R.id.nav_sing_out ->{

//                mSharePreferences.edit().clear().apply()
//
//                FirebaseAuth.getInstance().signOut()
//                Toast.makeText(this@MainActivity,"SignOut", Toast.LENGTH_SHORT).show()
//                val intent = Intent(this,IntroActivity::class.java)
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                startActivity(intent)
//                finish()
            }

        }

        drawer_layout?.closeDrawer(GravityCompat.START)
        return true

    }

    private fun requestLocationUpdates()
    {
        //Check for location permission
        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION)
        }
        else{

            mfusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallBack,
                Looper.getMainLooper())
        }
    }

    private val locationCallBack = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {

            locationResult?.let {

                for (location in it.locations){
                    var latitude = location.latitude
                    var longitude = location.longitude
                    city = getAddressFromLatLng(latitude,longitude)

                    et_location.setText(city)
                    setLongLat(latitude,longitude)
                    homeActivityApiRequest(city,latitude,longitude)
                }
            }
        }
    }

    private fun getAddressFromLatLng(latitude: Double, longitude: Double): String {
        val geoCoder = Geocoder(this, Locale.getDefault())
        var addressText = ""

        try {
            val addresses = geoCoder.getFromLocation(latitude, longitude, 1)
            if (!addresses!!.isEmpty()) {
                val address = addresses[0]
                val city = address.locality
                addressText = if (!city.isNullOrBlank()) {
                    city
                } else {
                    address.getAddressLine(0) ?: ""
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return addressText
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                requestLocationUpdates()
            }
            else{
                Toast.makeText(this,
                    "Location Permission Denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setUpActionBar() {
        toolbar_activity = findViewById(R.id.toolbar_activity)
        setSupportActionBar(toolbar_activity)

        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_navigation_menu)
            actionBar.title = "DastHub"
        }


        toolbar_activity.setNavigationOnClickListener {
            toggleDrawer()
        }

    }

    private fun homeActivityApiRequest(location:String,latitude: Double,longitude: Double)
    {
        Log.e("Lat/Long",latitude.toString())
        Log.e("Lat/Long",longitude.toString())

        val queue = Volley.newRequestQueue(this) // Create a request queue
        val ticketmasterApiClient = TicketmasterApiClient(resources.getString(R.string.ticket_master_api_key), queue)
        val yelpApiClient = YelpApiClient(resources.getString(R.string.yelp_api_key),queue)

        yelpApiClient.searchUpComingArtEvents(location,latitude,longitude,
            {
                hideProgressDialog()
                Log.e("YelpBusiness",it.toString())

                getGalleryList(it)

            },
            {
                Toast.makeText(this,"Api Error" , Toast.LENGTH_SHORT).show()
            })

        ticketmasterApiClient.searchUpComingMusicEvents(location,latitude,longitude,
            {
                hideProgressDialog()
//                Toast.makeText(this,"Connected to TicketMaster successfully" , Toast.LENGTH_SHORT).show()
                getFestivalList(it)

            },
            {
                Toast.makeText(this,"Api Error" , Toast.LENGTH_SHORT).show()
            })

        ticketmasterApiClient.searchUpComingArtEvents(location, latitude, longitude,
            {

                hideProgressDialog()
//                Toast.makeText(this,"Connected to TicketMaster successfully" , Toast.LENGTH_SHORT).show()
                getPlayList(it)

            },
            {
                Toast.makeText(this,"Api Error" , Toast.LENGTH_SHORT).show()
            }
        )

    }


    private fun setupDefaultRecyclerViews()
    {
        val placeHolderAdapter = PlaceholderAdapter()

        playsRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        playsRecyclerView.adapter = placeHolderAdapter

        festivalsRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        festivalsRecyclerView.adapter = placeHolderAdapter

        galleriesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        galleriesRecyclerView.adapter = placeHolderAdapter



    }

    private fun setupGalleryRecyclerView(galleryList: List<Gallery>) {

        galleriesRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        mGalleryAdapter = GalleryAdapter(this,galleryList)
        galleriesRecyclerView.adapter = mGalleryAdapter

        //Shows details of the clicked festival

        mGalleryAdapter.setOnClickListener(object : GalleryAdapter.OnClickListener {
            override fun onClick(position: Int, model: Gallery)
            {
                val intent = Intent(this@HomeActivity, GalleryPageActivity::class.java)
                intent.putExtra(EXTRA_GALLERY_DETAILS,model)
                startActivity(intent)
            }
        })
    }
    private fun setupFestivalRecyclerView (festivalList: List<Festival>)
    {
        festivalsRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        myFestivalAdapter = FestivalAdapter(this,festivalList)
        festivalsRecyclerView.adapter = myFestivalAdapter

        //Shows details of the clicked festival

        myFestivalAdapter.setOnClickListener(object : FestivalAdapter.OnClickListener {
            override fun onClick(position: Int, model: Festival)
            {
                val intent = Intent(this@HomeActivity, FestivalPageActivity::class.java)
                intent.putExtra(EXTRA_FESTIVAL_DETAILS,model)
                startActivity(intent)

            }
        })
    }

    private fun setupPlayRecyclerView (playList: List<Play>)
    {
        playsRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        myPlayAdapter = PlayAdapter(this,playList)
        playsRecyclerView.adapter = myPlayAdapter

//        Shows details of the clicked play
        myPlayAdapter.setOnClickListener(object : PlayAdapter.OnClickListener {
            override fun onClick(position: Int, model: Play)
            {
                val intent = Intent(this@HomeActivity, PlayPageActivity::class.java)
                intent.putExtra(EXTRA_PLAY_DETAILS,model)
                startActivity(intent)

            }
        })
    }

    private fun getGalleryList(galleries: List<Gallery>) {
        runOnUiThread {
            try {
                if (galleries.size > 0) {
                    galleriesRecyclerView.visibility = View.VISIBLE
                    setupGalleryRecyclerView(galleries)
                } else {
                    galleriesRecyclerView.visibility = View.GONE
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }



    private fun getFestivalList(festivals: List<Festival>) {
        try {
//            val dbHandler = DatabaseHandler(this)
//            val getFestivalList: festivals

            if (festivals.size > 0) {
//                message("List Seems to be populated")
//                tv_no_records?.visibility = View.GONE
                festivalsRecyclerView.visibility = View.VISIBLE
                setupFestivalRecyclerView(festivals)
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
    private fun getPlayList(plays: List<Play>) {
        try {

            if (plays.size > 0) {
//                message("List Seems to be populated")
//                tv_no_records?.visibility = View.GONE
                playsRecyclerView.visibility = View.VISIBLE
                setupPlayRecyclerView(plays)
            } else {
//                message("List Seems to be not populated")
                playsRecyclerView?.visibility = View.GONE
//                tv_no_records?.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            e.printStackTrace()
//            message("An error occurred: ${e.message}")
        }
    }

    companion object{
        var EXTRA_EVENTS_CITY = "extra_events_city"
        var EXTRA_FESTIVAL_DETAILS = "extra_place_details"
        var EXTRA_PLAY_DETAILS = "extra_place_details"
        var EXTRA_GALLERY_DETAILS = "extra_gallery_details"
        var EXTRA_LATITUDE = "extra_latitude"
        var EXTRA_LONGITUDE = "extra_longitude"
        const val PERMISSION_REQUEST_CODE = 1
        const val REQUEST_IMAGE_CAPTURE = 2
        const val PLACE_AUTOCOMPLETE_REQUEST_CODE = 3
        const val REQUEST_IMAGE_GALLERY = 4
        const val REQUEST_LOCATION = 5
        const val REQUEST_CHECK_SETTINGS = 6
    }

    override fun onClick(view: View?) {
        when (view?.id)
        {
            R.id.et_location ->{


                try {

                    val fields = listOf(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.ADDRESS)

                    val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN,fields).build(this@HomeActivity)

                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE)


                }catch (e:Exception)
                {
                    e.printStackTrace()
                }

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when(requestCode){

            PLACE_AUTOCOMPLETE_REQUEST_CODE ->{

                if (resultCode == RESULT_OK)
                {
                    val place = Autocomplete.getPlaceFromIntent(data)

                    val addressComponents = place.addressComponents

                    val stateComponent = addressComponents?.asList()

                    et_location?.hint =""
                    et_location?.setText(place.address)
                    // Set latitude and longitude first
                    val lat = place.latLng?.latitude
                    val long = place.latLng?.longitude

                    setLongLat(lat!!, long!!)

                    // Make API request after setting latitude and longitude
                    homeActivityApiRequest(place.address, lat!!, long!!)
                }

            }

            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }



}

