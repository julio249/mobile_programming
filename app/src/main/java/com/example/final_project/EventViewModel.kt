package com.example.final_project

import androidx.lifecycle.ViewModel

class EventViewModel (private val repository: FestivalRepository): ViewModel() {


    init {
        fetchEvents()
    }

    private fun fetchEvents() {
        TODO("Not yet implemented")

//        val eventList = repository.getEvents()

    }
}