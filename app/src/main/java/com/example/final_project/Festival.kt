package com.example.final_project

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
