package com.thoughworks.bookrecommendation.viewmodel

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughworks.bookrecommendation.http.ResultObserver
import com.thoughworks.bookrecommendation.model.data.Book
import com.thoughworks.bookrecommendation.model.data.PageResult
import com.thoughworks.bookrecommendation.repository.BookRepository

class BookListViewModel(context: Context) : ViewModel() {
    var booksLiveData = MediatorLiveData<List<Book>>()
    var bookDetailLiveData = MediatorLiveData<Book>()

    private val bookRepository = BookRepository(context)

    fun getBookList(catalogId: Int, pageIndex:Int = 1) {
        getDBBookList(catalogId)
        val observer: ResultObserver<PageResult> =
            object : ResultObserver<PageResult>() {
                override fun onSuccess(result: PageResult?) {
                    booksLiveData.postValue(result?.data)
                    bookRepository.updateDBBook(catalogId, result?.data)
                }

                override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                    e.printStackTrace()
                }
            }
        bookRepository.getBookList(catalogId, pageIndex, observer)
    }

    fun loadMoreBookList(catalogId: Int, pageIndex:Int = 1) {
        val observer: ResultObserver<PageResult> =
            object : ResultObserver<PageResult>() {
                override fun onSuccess(result: PageResult?) {
                    val bookList = mutableListOf<Book>()
                    booksLiveData.value?.let {
                        bookList.addAll(it)
                    }
                    result?.data?.let {
                        bookList.addAll(it)
                    }
                    booksLiveData.postValue(bookList)
                }

                override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                    e.printStackTrace()
                }
            }
        bookRepository.getBookList(catalogId, pageIndex, observer)
    }

    private fun getDBBookList(catalogId: Int) {
        booksLiveData.addSource(bookRepository.getListByCondition(catalogId)) {
            val books = Gson().fromJson<List<Book>>(
                Gson().toJson(it),object : TypeToken<List<Book>>() {}.type
            )
            booksLiveData.postValue(books)
        }
    }

}