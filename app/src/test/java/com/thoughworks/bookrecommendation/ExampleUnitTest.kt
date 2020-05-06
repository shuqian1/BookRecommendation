package com.thoughworks.bookrecommendation

import com.thoughworks.bookrecommendation.http.ApiService
import com.thoughworks.bookrecommendation.http.ApiService.Companion.DTYPE
import com.thoughworks.bookrecommendation.http.ApiService.Companion.KEY
import com.thoughworks.bookrecommendation.http.ResultObserver
import com.thoughworks.bookrecommendation.http.RetrofitFactory
import com.thoughworks.bookrecommendation.http.RetrofitFactory.Companion.executeResult
import com.thoughworks.bookrecommendation.model.data.Catalog
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    lateinit var apiService:ApiService
    @get:Rule
    val rule = TrampolineSchedulerRule()

    @Before
    fun setUp() {
        apiService = RetrofitFactory.createService()
    }

    @Test
     fun test_catalog_list() {
        apiService.getCatalogList(KEY, DTYPE)
            .executeResult(object : ResultObserver<List<Catalog>>() {
                override fun onSuccess(result: List<Catalog>?) {
                    Assert.assertEquals(result?.size?.compareTo(0),1)
                }

                override fun onFailure(e: Throwable, isNetWorkError: Boolean) {

                }
            })

    }
}
