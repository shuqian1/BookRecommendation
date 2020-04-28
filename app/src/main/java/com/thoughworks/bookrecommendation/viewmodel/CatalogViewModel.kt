package com.thoughworks.bookrecommendation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thoughworks.bookrecommendation.http.ResultObserver
import com.thoughworks.bookrecommendation.model.data.Catalog
import com.thoughworks.bookrecommendation.repository.CatalogRepository

class CatalogViewModel : ViewModel() {
    val catalogLiveData = MutableLiveData<List<Catalog>>()

    private val catalogRepository = CatalogRepository()

    fun getCatalogList() {
        val observer: ResultObserver<List<Catalog>> =
            object : ResultObserver<List<Catalog>>() {
                override fun onSuccess(result: List<Catalog>?) {
                    catalogLiveData.postValue(result)
                }

                override fun onFailure(e: Throwable, isNetWorkError: Boolean) {

                }

            }

        catalogRepository.getList(observer)

    }

}