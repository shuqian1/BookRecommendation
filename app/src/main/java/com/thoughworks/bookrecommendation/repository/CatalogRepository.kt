package com.thoughworks.bookrecommendation.repository

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughworks.bookrecommendation.db.AppDatabase
import com.thoughworks.bookrecommendation.db.CatalogDao
import com.thoughworks.bookrecommendation.db.DBCatalog
import com.thoughworks.bookrecommendation.http.ApiService
import com.thoughworks.bookrecommendation.http.ResultObserver
import com.thoughworks.bookrecommendation.http.RetrofitFactory
import com.thoughworks.bookrecommendation.http.RetrofitFactory.Companion.executeResult
import com.thoughworks.bookrecommendation.model.data.Catalog


class CatalogRepository(context: Context) {
    var database: AppDatabase = AppDatabase.getDatabaseInstance(context)

    fun getList(observer: ResultObserver<List<Catalog>>) {
        RetrofitFactory.createService()
            .getCatalogList(ApiService.KEY, ApiService.DTYPE)
            .executeResult(observer)
    }

    fun updateDBCatalog(result: List<Catalog>?) {
        val catalogs = Gson().fromJson<List<DBCatalog>>(
            Gson().toJson(result), object: TypeToken<List<DBCatalog>>() {}.type
        )
        CatalogAsyncTask().execute(catalogs)
    }

    fun getAllCatalog(): LiveData<List<DBCatalog>> {
        return database.catalogDao().getAll()
    }

    inner class CatalogAsyncTask : AsyncTask<List<DBCatalog>, Void?, Void?>() {
        override fun doInBackground(vararg params: List<DBCatalog>): Void? {
            database.catalogDao().deleteAll()
            database.catalogDao().insertBatch(params[0])
            return null
        }
    }



}