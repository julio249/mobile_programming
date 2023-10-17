package com.example.final_project.network

import com.example.final_project.models.Festival
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface EventApiService {
    @GET("events.json?apikey=prGEajXbRdrOnNaeBNi8Z5VdADIvyJfD")
    fun getEvents(@Header("Accept") accept: String): Call<Festival>

}