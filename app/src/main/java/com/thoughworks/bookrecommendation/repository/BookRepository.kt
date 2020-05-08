package com.thoughworks.bookrecommendation.repository

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughworks.bookrecommendation.db.AppDatabase
import com.thoughworks.bookrecommendation.db.AppDatabase.Companion.PAGE_SIZE
import com.thoughworks.bookrecommendation.db.DBBook
import com.thoughworks.bookrecommendation.http.ApiService
import com.thoughworks.bookrecommendation.http.ResultObserver
import com.thoughworks.bookrecommendation.http.RetrofitFactory
import com.thoughworks.bookrecommendation.http.RetrofitFactory.Companion.executeResult
import com.thoughworks.bookrecommendation.model.data.Book
import com.thoughworks.bookrecommendation.model.data.PageResult


class BookRepository(context: Context) {

    var database: AppDatabase = AppDatabase.getDatabaseInstance(context)
    
    fun getBookList(catalogId: Int, pageIndex: Int = 1, observer: ResultObserver<PageResult>) {
        val startIndex = (pageIndex - 1) * PAGE_SIZE
        RetrofitFactory.createService()
            .getBookList(ApiService.KEY, ApiService.DTYPE, catalogId, startIndex, PAGE_SIZE)
            .executeResult(observer)
    }

    fun getListByCondition(catalogId: Int): LiveData<List<DBBook>> {
        return database.bookDao().getListByCondition(catalogId, PAGE_SIZE)
    }

    fun updateDBBook(catalogId: Int, result: List<Book>?) {
        val books = Gson().fromJson<List<DBBook>>(
            Gson().toJson(result), object: TypeToken<List<DBBook>>() {}.type
        )
        books.forEach {
            it.catalogId = catalogId
        }
        BookAsyncTask().execute(BookTaskParam(catalogId, books))
    }

    inner class BookAsyncTask : AsyncTask<BookTaskParam, Void?, Void?>() {
        override fun doInBackground(vararg params: BookTaskParam): Void? {
            database.bookDao().deleteByCatalog(params[0].catalogId)
            database.bookDao().insertBatch(params[0].bookList)
            return null
        }
    }

    inner class BookTaskParam(val catalogId: Int, val bookList: List<DBBook>)


}