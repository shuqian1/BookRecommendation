package com.thoughworks.bookrecommendation.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughworks.bookrecommendation.http.ResultObserver
import com.thoughworks.bookrecommendation.model.data.Book
import com.thoughworks.bookrecommendation.model.data.PageResult
import com.thoughworks.bookrecommendation.repository.BookRepository

class BookListViewModel(val context: Context) : ViewModel() {
    var booksLiveData = MediatorLiveData<List<Book>>()
    var bookDetailLiveData = MediatorLiveData<Book>()
    var noMoreBookFlag = MediatorLiveData<Boolean>()

    var bookRepository = BookRepository(context)

    fun getBookList(catalogId: Int, pageIndex: Int = 1) {
        getDBBookList(catalogId)
        val observer: ResultObserver<PageResult> =
            object : ResultObserver<PageResult>() {
                override fun onSuccess(result: PageResult?) {
                    booksLiveData.postValue(result?.data)
                    bookRepository.updateDBBook(catalogId, result?.data)
                }

                override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                    if (isNetWorkError) {
                        Toast.makeText(context, "网络错误", Toast.LENGTH_SHORT).show()
                    }
                    e.printStackTrace()
                }

                override fun onBusinessFail(code: Int, message: String) {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            }
        bookRepository.getBookList(catalogId, pageIndex, observer)
    }

    fun loadMoreBookList(catalogId: Int, pageIndex: Int = 1) {
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
                override fun onBusinessFail(code: Int, message: String) {
                    if(code == 202) {
                        noMoreBookFlag.postValue(true)
                    }
                }
            }
        bookRepository.getBookList(catalogId, pageIndex, observer)
    }

    private fun getDBBookList(catalogId: Int) {
        booksLiveData.addSource(bookRepository.getListByCondition(catalogId)) {
            val books = Gson().fromJson<List<Book>>(
                Gson().toJson(it), object : TypeToken<List<Book>>() {}.type
            )
            booksLiveData.postValue(books)
        }
    }

}