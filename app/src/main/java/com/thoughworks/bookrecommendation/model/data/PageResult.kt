package com.thoughworks.bookrecommendation.model.data

data class PageResult(
    val data: List<Book>,
    val pn: Int,
    val rn: String,
    val totalNum: String
)