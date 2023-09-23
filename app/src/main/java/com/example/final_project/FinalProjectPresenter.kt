package com.example.final_project

import android.app.usage.UsageEvents

class FinalProjectPresenter {

    // EventListPresenterImpl.kt
    class EventListPresenterImpl(private val view: FinalProjectContract.EventListView, private val repository: FinalProjectContract.EventRepository) :
        FinalProjectContract.EventListPresenter {

        override fun loadEvents() {
            val events = repository.getEvents()
            if (events.isNotEmpty()) {
                view.showEvents(events)
            } else {
                view.showError("No events found.")
            }
        }

        override fun onEventItemSelected(event: UsageEvents.Event) {
            // Handle event item selection, e.g., navigate to event details screen.
        }
    }

}