package com.thoughworks.bookrecommendation.model.entity

import com.google.gson.annotations.SerializedName

class BaseResponse<T>(
    @SerializedName("resultcode")
    val resultCode: Int,
    val reason: String,
    @SerializedName("error_code")
    val errorCode: Int,
    val result: T
) {
    fun isSuccess(): Boolean {
        return resultCode == 200
    }
}