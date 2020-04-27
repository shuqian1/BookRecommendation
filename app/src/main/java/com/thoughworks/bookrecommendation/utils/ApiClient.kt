package com.thoughworks.bookrecommendation.utils

import okhttp3.*
import java.io.IOException

object ApiClient {

    val client by lazy(LazyThreadSafetyMode.NONE) { OkHttpClient.Builder().build() }

    fun httpGet(url:String, callback: Callback) {
        val request = Request
            .Builder()
            .url(url)
            .build()
        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e.printStackTrace())
                callback.onFailure(call, e)
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onResponse(call, response)
            }

        })

    }

}