package com.example.final_project.models

import java.io.Serializable
import java.util.Date

data class Festival(
    val id: Int,
    val title: String,
    val location: String,
    val startDate: String,
    val endDate: String,
//    val ticketPrice: Double,
//    val performers: List<String>
):Serializable

data class FestivalResponse(
    val _embedded: Embedded?
)

data class Embedded(
    val events: List<Festival>?
)

