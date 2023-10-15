package com.example.final_project

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity

class FestivalActivity:AppCompatActivity() , OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.festival_activity)
    }


    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}