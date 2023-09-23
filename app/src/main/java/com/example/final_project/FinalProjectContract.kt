package com.example.final_project

import android.app.usage.UsageEvents

class FinalProjectContract {
    // EventListView.kt
    interface EventListView {
        fun showEvents(events: List<UsageEvents.Event>)
        fun showError(message: String)
    }

    // EventListPresenter.kt
    interface EventListPresenter {
        fun loadEvents()
        fun onEventItemSelected(event: UsageEvents.Event)
    }

    // EventRepository.kt (if you have a separate model)
    interface EventRepository {
        fun getEvents(): List<UsageEvents.Event>
    }

}