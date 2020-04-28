package com.thoughworks.bookrecommendation.repository

import com.thoughworks.bookrecommendation.http.ApiService
import com.thoughworks.bookrecommendation.http.ResultObserver
import com.thoughworks.bookrecommendation.http.RetrofitFactory
import com.thoughworks.bookrecommendation.http.RetrofitFactory.Companion.executeResult
import com.thoughworks.bookrecommendation.model.data.Catalog


class CatalogRepository {

    fun getList(observer: ResultObserver<List<Catalog>>) {
        RetrofitFactory.createService().getCatalogList(
            ApiService.KEY, ApiService.DTYPE)
            .executeResult(observer)
    }

}