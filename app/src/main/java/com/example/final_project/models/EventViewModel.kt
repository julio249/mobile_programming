package com.example.final_project.models

import androidx.lifecycle.ViewModel
import com.example.final_project.database.FestivalRepository

class EventViewModel (private val repository: FestivalRepository): ViewModel() {


    init {
        fetchEvents()
    }

    private fun fetchEvents() {
        TODO("Not yet implemented")

//        val eventList = repository.getEvents()

    }
}