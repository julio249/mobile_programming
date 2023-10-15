package com.example.final_project.models

import java.io.Serializable
import java.util.Date

data class Exposition(
    val id: Int,
    val title: String,
    val location: String,
    val startDate: String,
    val endDate: String,
//    val ticketPrice: Double,
//    val performers: List<String>
):Serializable
