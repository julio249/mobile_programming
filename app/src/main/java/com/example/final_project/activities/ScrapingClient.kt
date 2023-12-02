package com.example.final_project.activities

import android.provider.DocumentsContract
import androidx.lifecycle.MutableLiveData
import com.example.final_project.models.Festival
//import it.skrape.core.document
//import it.skrape.fetcher.HttpFetcher
//import it.skrape.fetcher.extractIt
//import it.skrape.fetcher.skrape
//import it.skrape.selects.html5.*


class ScrapingClient {
    private val websiteUrl = "https://georgesmajesticlounge.com/shows/"
    private var _festivals: MutableLiveData<List<Festival>> = MutableLiveData(emptyList())


    data class MyScrapedData(
        val userName: String,
        val link: String
    )

//    fun main() {
//        val githubUserData = skrape {
//            val url = "http://www.website"
//
//            extract {
//                MyScrapedData(
//                    userName = el("div.CollapsiblePanel").text(),
//                    link = el("table tr td a").attr("href")
//                    el( )
//                )
//            }
//        }
//        println("selected user: ${githubUserData.userName}")
//        println("selected link: ${githubUserData.link}")
//
//        // will print:
//        // Today's Interest (1)
//        // link info i need in here
//    }
//}
}

