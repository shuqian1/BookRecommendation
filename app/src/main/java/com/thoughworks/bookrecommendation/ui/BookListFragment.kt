package com.thoughworks.bookrecommendation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.thoughworks.bookrecommendation.R
import com.thoughworks.bookrecommendation.databinding.BookListFragmentBinding
import com.thoughworks.bookrecommendation.viewmodel.BookListViewModel

class BookListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val book = BookListViewModel(this.requireContext())
        val bookListFragmentBinding = DataBindingUtil.inflate<BookListFragmentBinding>(
            inflater,
            R.layout.book_list_fragment,
            container,
            false
        )
        bookListFragmentBinding.book = book
        bookListFragmentBinding.lifecycleOwner = this
        book.getCatalogList(264)
        return bookListFragmentBinding.root
    }
}