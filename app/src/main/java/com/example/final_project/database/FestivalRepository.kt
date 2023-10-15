package com.example.final_project.database

import android.content.Context
import com.example.final_project.database.DatabaseHandler
import com.example.final_project.models.Festival

class FestivalRepository(private val context: Context) {

    private val databaseHandler = DatabaseHandler (context)

    fun getFestivals(): List<Festival> {

        return listOf(
            Festival(1, "Beyond Wonderland", "San Bernardino", "March 24", "March 25"),
            Festival(2, "Tomorrowland", "Boom", "July 21", "July 23"),
            Festival(3, "Ultra Music Festival", "Miami", "March 24", "March 26"),
            Festival(4, "Coachella", "Indio", "April 14", "April 16"),
            Festival(5, "Lollapalooza", "Chicago", "August 3", "August 6"),
            Festival(6, "Burning Man", "Black Rock City", "August 27", "September 4"),
            Festival(7, "Glastonbury Festival", "Pilton", "June 21", "June 25")
        )

    }

    fun populateFestivalTable()
    {
        val festivals = getFestivals()
        for (festival in festivals)
        {
            databaseHandler.addFestival(festival)
        }
    }


}