package com.thoughworks.bookrecommendation.http

import com.thoughworks.bookrecommendation.db.AppDatabase.Companion.PAGE_SIZE
import com.thoughworks.bookrecommendation.model.data.Book
import com.thoughworks.bookrecommendation.model.data.Catalog
import com.thoughworks.bookrecommendation.model.data.PageResult
import com.thoughworks.bookrecommendation.model.entity.BaseResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    companion object {
        const val API_SERVER_URL = "http://apis.juhe.cn/goodbook/"
        const val KEY = "4c17a91097e5c8134fba78dbcedd8a42"
        const val DTYPE = "json"
    }

    @GET("catalog")
    fun getCatalogList(
        @Query("key") key: String,
        @Query("dtype") dtype: String
    ): Observable<BaseResponse<List<Catalog>>>

    @GET("query")
    fun getBookList(
        @Query("key") key: String,
        @Query("dtype") dtype: String,
        @Query("catalog_id") catalog_id: Int,
        @Query("pn") startIndex: Int = 0,
        @Query("rn") pageSize: Int = PAGE_SIZE
    ): Observable<BaseResponse<PageResult>>
}