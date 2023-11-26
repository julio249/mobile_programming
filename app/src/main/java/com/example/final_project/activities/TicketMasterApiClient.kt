package com.example.final_project.activities

import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.example.final_project.models.Artist
import com.example.final_project.models.Festival
import com.example.final_project.models.Play
import org.json.JSONException
import org.json.JSONObject

class TicketmasterApiClient(private val apiKey: String, private val queue: RequestQueue) {
    private val baseUrl = "https://app.ticketmaster.com/discovery/v2/"


    //Provides information about event occurring in a specific location
    fun searchEventsByKeyword(location:String,latitude: Double,longitude: Double,
                                    onSuccess: (List<Festival>) -> Unit,
                                    onError: (String) -> Unit
    ) {


        val radius = 50
        val url = "$baseUrl/events.json?keyword=tiesto&apikey=$apiKey&sort=date,asc&latlong=$latitude,$longitude&radius=$radius"
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                val nameAndTypeList = extractFestivalFromJSON(response)
                onSuccess(nameAndTypeList)
            },
            { error ->
                onError(error.message ?: "An error occurred")
            })

        Log.i("ByLocation", request.toString())
        queue.add(request)
    }

    fun searchUpComingMusicEvents(location:String,latitude: Double,longitude: Double,
                                  onSuccess: (List<Festival>) -> Unit,
                                  onError: (String) -> Unit
    ) {


        val radius = 50
        val url = "$baseUrl/events.json?classificationName=pop&apikey=$apiKey&sort=date,asc&latlong=$latitude,$longitude&radius=$radius"
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                val nameAndTypeList = extractFestivalFromJSON(response)
                onSuccess(nameAndTypeList)
            },
            { error ->
                onError(error.message ?: "An error occurred")
            })

        Log.i("ByLocation", request.toString())
        queue.add(request)
    }



    fun searchUpComingArtEvents(location: String, latitude: Double,longitude:Double,
        onSuccess: (List<Play>) -> Unit,
        onError: (String) -> Unit
    ) {
        val radius = 50
        val url = "$baseUrl/events.json?classificationName=Theatre&apikey=$apiKey&sort=date,asc&latlong=$latitude,$longitude&radius=$radius"
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                val nameAndTypeList = extractPlayFromJSON(response)

                onSuccess(nameAndTypeList)
            },
            { error ->
                onError(error.message ?: "An error occurred")
            })

        Log.i("ByLocation", request.toString())
        queue.add(request)
    }
    private fun extractFestivalFromJSON(response: JSONObject): List<Festival> {
        val nameAndTypeList = mutableListOf<Festival>()
        val artistList = mutableListOf<Artist>()

        try {
            val embedded = response.optJSONObject("_embedded")
            val events = embedded?.optJSONArray("events")

            if (events != null) {
                for (i in 0 until events.length()) {
                    val event = events.getJSONObject(i)
                    val name = event.optString("name")
                    val type = event.optString("type")
                    val ticketUrl = event.optString("url")


                    val embedded = event.optJSONObject("_embedded")
                    val venueList = embedded?.optJSONArray("venues")
                    val attractionsList = embedded?.optJSONArray("attractions")


                    val venue = venueList?.getJSONObject(0)
                    val nameVenue = venue!!.optString("name")

                    val dates = event.optJSONObject("dates")
                    val start = dates?.optJSONObject("start")
                    val localDate = start?.optString("localDate")

                    val images = event.optJSONArray("images")
                    val image = images.getJSONObject(1)
                    val urlImage = image.optString("url")

                    val artistList = mutableListOf<Artist>()
                    if (attractionsList != null) {
                        for (j in 0 until attractionsList.length()) {
                            val attraction = attractionsList.getJSONObject(j)
                            val artistName = attraction.optString("name")
                            val imageArtist = attraction.optJSONArray("images")
                            val imageUrl = imageArtist.getJSONObject(0)
                            // Add the artist to the list
                            artistList.add(Artist(artistName,imageUrl.optString("url")))
                        }
                    }

                    nameAndTypeList.add(Festival(name,localDate, nameVenue,urlImage,ticketUrl,artistList))
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return nameAndTypeList
    }
    private fun extractPlayFromJSON(response: JSONObject): List<Play> {
        val nameAndTypeList = mutableListOf<Play>()

        try {
            val embedded = response.optJSONObject("_embedded")
            val events = embedded?.optJSONArray("events")

            if (events != null) {
                for (i in 0 until events.length()) {
                    val event = events.getJSONObject(i)
                    val name = event.optString("name")
                    val type = event.optString("type")
                    val ticketUrl = event.optString("url")


                    val embedded = event.optJSONObject("_embedded")
                    val venueList = embedded?.optJSONArray("venues")

                    val dates = event.optJSONObject("dates")
                    val start = dates?.optJSONObject("start")
                    val localDate = start?.optString("localDate")

                    val venue = venueList?.getJSONObject(0)
                    val nameVenue = venue!!.optString("name")

                    val images = event.optJSONArray("images")
                    val image = images.getJSONObject(1)
                    val urlImage = image.optString("url")

                    nameAndTypeList.add(Play(name,nameVenue, localDate,urlImage))
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return nameAndTypeList
    }


}


