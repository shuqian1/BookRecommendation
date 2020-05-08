package com.thoughworks.bookrecommendation

import android.content.Context
import android.widget.Toast
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MediatorLiveData
import com.thoughworks.bookrecommendation.db.DBBook
import com.thoughworks.bookrecommendation.repository.BookRepository
import com.thoughworks.bookrecommendation.viewmodel.BookListViewModel
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.*


class BookListViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var rule1 = TrampolineSchedulerRule()


    @Test
    internal fun `get db books when internet error`() {
        val bookRepository = Mockito.mock(BookRepository::class.java)
        val context = Mockito.mock(Context::class.java)
        val bookListViewModel = BookListViewModel(context)
        bookListViewModel.bookRepository = bookRepository
        val bookLiveData = MediatorLiveData<List<DBBook>>()
        bookLiveData.postValue(listOf(DBBook(1, 1, "20190909", "2", "img", "http", "3","sub1", "sub2", "tags", "title")))
        Mockito.`when`(bookRepository.getListByCondition(1)).thenReturn(bookLiveData)
        bookListViewModel.getBookList(1, 1)
        bookListViewModel.booksLiveData.observeForever {
            assertEquals(it.size, 1)
        }
    }

    @Test
    internal fun `get web books when internet success`() {
        val bookRepository = Mockito.mock(BookRepository::class.java)
        val context = Mockito.mock(Context::class.java)
        val bookListViewModel = BookListViewModel(context)
        val bookLiveData = MediatorLiveData<List<DBBook>>()
        bookLiveData.postValue(listOf(DBBook(1, 1, "20190909", "2", "img", "http", "3","sub1", "sub2", "tags", "title")))
        Mockito.`when`(bookRepository.getListByCondition(1)).thenReturn(bookLiveData)
        bookListViewModel.getBookList(1, 1)
        assertEquals(bookListViewModel.booksLiveData.value?.size, 20)
    }
}