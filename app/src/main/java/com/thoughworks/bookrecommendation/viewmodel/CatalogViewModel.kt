package com.thoughworks.bookrecommendation.viewmodel

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughworks.bookrecommendation.http.ResultObserver
import com.thoughworks.bookrecommendation.model.data.Catalog
import com.thoughworks.bookrecommendation.repository.CatalogRepository

class CatalogViewModel(val catalogRepository: CatalogRepository) : ViewModel() {
    var catalogLiveData = MediatorLiveData<List<Catalog>>()
    var status = MutableLiveData<Boolean>()
    var errorMessage = ""


    fun getCatalogList() {
        getDBCatalogList()
        val observer: ResultObserver<List<Catalog>> =
            object : ResultObserver<List<Catalog>>() {
                override fun onSuccess(result: List<Catalog>?) {
                    status.postValue(true)
                    catalogLiveData.postValue(result)
                    catalogRepository.updateDBCatalog(result)
                }

                override fun onFailure(e: Throwable, isNetWorkError: Boolean) {

                }

                override fun onBusinessFail(code: Int, message: String) {
                    status.postValue(false)
                    errorMessage = message
                }
            }
        catalogRepository.getList(observer)
    }



    private fun getDBCatalogList() {
        catalogLiveData.addSource(catalogRepository.getAllCatalog()) {
            val catalogs = Gson().fromJson<List<Catalog>>(
                Gson().toJson(it),object : TypeToken<List<Catalog>>() {}.type
            )
            catalogLiveData.postValue(catalogs)
        }
    }

}