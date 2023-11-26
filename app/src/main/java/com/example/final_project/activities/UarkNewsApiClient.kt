package com.example.final_project.activities

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.final_project.models.News
import org.simpleframework.xml.core.Persister
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader

class UarkNewsApiClient(private val context: Context) {

    private val baseUrl = "https://campusdata.uark.edu/apiv2/"

    fun searchNews(
        keyWords: String,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        val url = "$baseUrl/articles/SearchArticle?\$orderby=publishDate+desc&\$top=5&\$filter=(substringof(%27concert%27,content))"

        val request = StringRequest(Request.Method.GET, url,
            { response ->

                val trimmedResponse = response.trim()
                parseXml(trimmedResponse)
            },
            { error ->
                onError(error.message ?: "An error occurred")
            })

        // Create a request queue
        val requestQueue = Volley.newRequestQueue(context.applicationContext)

        // Add the StringRequest to the queue
        requestQueue.add(request)
    }



    private fun parseXml(xmlString: String): List<News> {
        val newsList = mutableListOf<News>()
        Log.e("UarkResponse",xmlString)
        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val parser = factory.newPullParser()

            parser.setInput(StringReader(xmlString))

            var eventType = parser.eventType
            var currentNews: News? = null

            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        if (parser.name.equals("Article", ignoreCase = true)) {
                            currentNews = News()
                        } else if (currentNews != null) {
                            when (parser.name) {
                                "headline" -> currentNews.title = parser.nextText()
                                "brief" -> currentNews.brief = parser.nextText()
                                "image" -> currentNews.image = parser.nextText()
                            }
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        if (parser.name.equals("Article", ignoreCase = true) && currentNews != null) {
                            newsList.add(currentNews)
                            currentNews = null
                        }
                    }
                }
                eventType = parser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("UARK", "Error parsing XML: ${e.message}")
        }

        return newsList
    }


}
