package com.thoughworks.bookrecommendation.model.data

data class Book(
    val bytime: String,
    val catalog: String,
    val img: String,
    val online: String,
    val reading: String,
    val sub1: String,
    val sub2: String,
    val tags: String,
    val title: String
){
     fun getLink():String {
        val list = online.split(" ")
        return list.joinToString("\n")
    }
}