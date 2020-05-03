package com.thoughworks.bookrecommendation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.thoughworks.bookrecommendation.R
import com.thoughworks.bookrecommendation.databinding.DetailsFragmentBinding
import com.thoughworks.bookrecommendation.viewmodel.BookListViewModel
import com.thoughworks.bookrecommendation.viewmodel.BookListViewModelFactory


class BookDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val detailBinding = DataBindingUtil.inflate<DetailsFragmentBinding>(
            inflater,
            R.layout.details_fragment,
            container,
            false
        )
        val bookViewModel =
            ViewModelProviders.of(activity!!, BookListViewModelFactory(this.requireContext())).get<BookListViewModel>(
                BookListViewModel::class.java)
        detailBinding.bookDetail = bookViewModel
        return detailBinding.root
    }

    override fun onHiddenChanged(hidden: Boolean) {
        if(!hidden) {
            (activity as BookListActivity).modifyCurrentFragment(this)
        }
    }
}