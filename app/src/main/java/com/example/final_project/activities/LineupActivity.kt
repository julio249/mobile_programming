package com.example.final_project.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R
import com.example.final_project.adapters.LineupAdapter
import com.example.final_project.models.Artist
import com.example.final_project.models.Festival

class LineupActivity : AppCompatActivity() {

    private lateinit var lineupRecyclerView: RecyclerView
    private lateinit var lineupAdapter: LineupAdapter

    private var toolbarLineup: Toolbar? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lineup)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        lineupRecyclerView = findViewById(R.id.rv_lineup)
        toolbarLineup = findViewById(R.id.toolBar_lineup)

        setSupportActionBar(toolbarLineup)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Line-up"

        var festivalDetailModel : Festival? = null

        if (intent.hasExtra(FestivalPageActivity.LINEUP_INFO))
        {
            festivalDetailModel = intent.getParcelableExtra(FestivalPageActivity.LINEUP_INFO)
        }

        getArtistList(festivalDetailModel!!.lineup)

        // TODO: Call function that populates the line-up recycler view

        toolbarLineup!!.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    private fun getArtistList(artists: List<Artist>)
    {
        try {

            if (artists.size > 0) {

                lineupRecyclerView.visibility = View.VISIBLE
                setupLineupRecyclerView(artists)
            } else {
                lineupRecyclerView?.visibility = View.GONE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setupLineupRecyclerView(artists: List<Artist>)
    {
        lineupRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        lineupAdapter = LineupAdapter(this,artists)
        lineupRecyclerView.adapter = lineupAdapter
    }



}