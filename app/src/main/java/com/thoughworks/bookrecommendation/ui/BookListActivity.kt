package com.thoughworks.bookrecommendation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.thoughworks.bookrecommendation.R
import com.thoughworks.bookrecommendation.viewmodel.BookListViewModel

class BookListActivity : AppCompatActivity() {

    private lateinit var bookViewModel: BookListViewModel
    private val bookListFragment = BookListFragment()
    val bookDetailFragment = BookDetailFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)
        bookViewModel = BookListViewModel(this.applicationContext)
        addFragment(bookListFragment)
    }

    private var currentFragment = Fragment()

    fun switchFragment(targetFragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        if (!targetFragment.isAdded) {
            transaction.hide(currentFragment)
                .add(R.id.bookListFragment, targetFragment)
                .addToBackStack(currentFragment.id.toString())
                .commit()
        } else {
            transaction.hide(currentFragment)
                .show(targetFragment)
                .addToBackStack(currentFragment.id.toString())
                .commit()
        }
        currentFragment = targetFragment
    }

    private fun addFragment(targetFragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.bookListFragment, targetFragment)
            .commit()
        currentFragment = targetFragment
    }

    fun modifyCurrentFragment(targetFragment: Fragment) {
        currentFragment = targetFragment
    }

}
