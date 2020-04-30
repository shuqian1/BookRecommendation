package com.thoughworks.bookrecommendation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thoughworks.bookrecommendation.R

class BookListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)

        val bookListFragment = BookListFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.bookListFragment, bookListFragment).commit()
    }
}
