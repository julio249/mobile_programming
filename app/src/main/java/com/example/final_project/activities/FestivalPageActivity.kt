package com.example.final_project.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.final_project.R
import com.example.final_project.models.Festival

class FestivalPageActivity: AppCompatActivity() {

    private var festivalTitle: TextView? =null
    private var festivalLocation: TextView? =null
    private var festivalStartDate: TextView? =null
    private var festivalEndDate: TextView? =null
    private var toolbarFestivalDetail: Toolbar?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_festival_detail)

        festivalTitle = findViewById(R.id.tv_title)
        festivalLocation = findViewById(R.id.tv_location)
        festivalStartDate = findViewById(R.id.tv_startDate)
        festivalEndDate = findViewById(R.id.tv_endDate)
        toolbarFestivalDetail = findViewById(R.id.toolBar)
        var festivalDetailModel : Festival? = null

        if (intent.hasExtra(HomeActivity.EXTRA_FESTIVAL_DETAILS))
        {
            festivalDetailModel = intent.getSerializableExtra(HomeActivity.EXTRA_FESTIVAL_DETAILS) as Festival

        }
        if (festivalDetailModel !=null){

            setSupportActionBar(toolbarFestivalDetail)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = ""
            toolbarFestivalDetail!!.setNavigationOnClickListener{
                onBackPressed()
            }

            festivalTitle?.text = festivalDetailModel.title
            festivalLocation?.text = festivalDetailModel.location
            festivalStartDate?.text = festivalDetailModel.startDate
            festivalEndDate?.text = festivalDetailModel.endDate

        }


    }
}
