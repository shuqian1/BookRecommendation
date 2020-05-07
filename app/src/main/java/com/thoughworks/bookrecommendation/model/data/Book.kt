package com.thoughworks.bookrecommendation.model.data

import android.os.Build
import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.text.Spanned
import android.text.TextUtils.isEmpty
import androidx.annotation.RequiresApi

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
) {
    @RequiresApi(Build.VERSION_CODES.N)
    fun getLink(): Spanned? {
        if (isEmpty(online)) {
            return null
        }
        var resultLink = ""
        val list = online.split(" ")
        for(element in list) {
            val itemLink = element.split(":")
            if(itemLink.size > 2) {
                resultLink +="<a href='${itemLink[1]}:${itemLink[2]}'>${itemLink[0]}</a>&nbsp;&nbsp;&nbsp;&nbsp; "
            }
        }
        return Html.fromHtml(resultLink, FROM_HTML_MODE_LEGACY)
    }
}