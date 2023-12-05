package com.example.final_project.activities

import android.util.Log
//import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.example.final_project.models.Artist
import com.example.final_project.models.Festival
import com.example.final_project.models.Gallery
import com.example.final_project.models.Play
import org.json.JSONException
import org.json.JSONObject
import okhttp3.OkHttpClient
import okhttp3.Request

class YelpApiClient(private val apiKey: String, private val queue: RequestQueue) {
    private val baseUrl = "https://api.yelp.com/v3/"

    fun searchUpComingArtEvents(location:String,latitude: Double,longitude: Double,
                                  onSuccess: (List<Gallery>) -> Unit,
                                  onError: (String) -> Unit
    ) {

        val radius = 40000
        val client = OkHttpClient()

            val url = "https://api.yelp.com/v3/businesses/search?latitude=$latitude&longitude=$longitude&term=galleries&radius=$radius&sort_by=best_match&limit=20"

            val request = Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer $apiKey")
                .build()

            client.newCall(request).enqueue(object : okhttp3.Callback {
                override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                    val responseBody = response.body!!.string()
                    if (response.isSuccessful && !responseBody.isNullOrBlank())
                    {
                        Log.e("YelpResponse", responseBody)
                        val nameAndTypeList = extractGalleryFromJSON(responseBody)
                        onSuccess(nameAndTypeList)
                    }
                }

                override fun onFailure(call: okhttp3.Call, e: java.io.IOException) {

                    onError(e.message ?: "An error occurred")

                }
            })

    }


    private fun extractGalleryFromJSON(response: String): List<Gallery> {
        val galleryList = mutableListOf<Gallery>()

        try {
            val jsonResponse = JSONObject(response)
            val businessesArray = jsonResponse.getJSONArray("businesses")

            for (i in 0 until businessesArray.length()) {
                val businessObject = businessesArray.getJSONObject(i)


                val imageUrl = businessObject.optString("image_url")
                val name = businessObject.optString("name")
                val url = businessObject.optString("url")


                // Extracting location details
                val locationObject = businessObject.getJSONObject("location")
                val address1 = locationObject.optString("address1")
                val city = locationObject.optString("city")
                val state = locationObject.optString("state")
                val zipCode = locationObject.optString("zip_code")

                val gallery = Gallery(name, imageUrl, url,address1,city,state,zipCode)


                galleryList.add(gallery)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return galleryList
    }




}


