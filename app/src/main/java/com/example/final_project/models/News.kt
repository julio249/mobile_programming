package com.example.final_project.models

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "Article", strict = false)
data class News(
    @field:Element(name = "headline", required = false)
    var title: String? = "",

    @field:Element(name = "brief", required = false)
    var brief: String? = "",

    @field:Element(name = "image", required = false)
    var image: String? = ""
)

