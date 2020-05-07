package com.thoughworks.bookrecommendation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.thoughworks.bookrecommendation.R
import com.thoughworks.bookrecommendation.model.data.Book
import com.thoughworks.bookrecommendation.viewmodel.BookListViewModel
import com.thoughworks.bookrecommendation.viewmodel.BookListViewModelFactory
import kotlinx.android.synthetic.main.book_list_fragment.view.*


class BookListFragment : Fragment() {

    private val bookAdapter by lazy { BookAdapter() }

    var pageIndex = 1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val catalogId = activity?.intent?.extras?.getString("id")?.toInt()?: 0

        val rootView = inflater.inflate(R.layout.book_list_fragment, container, false)

        rootView.rv_book_list?.apply {
            layoutManager = LinearLayoutManager(activity)
            emptyStateView = rootView.emptyView
            loadingStateView = rootView.loadingView
            adapter = bookAdapter
        }

        val bookViewModel =
            ViewModelProviders.of(activity!!, BookListViewModelFactory(this.requireContext())).get<BookListViewModel>(BookListViewModel::class.java)
        bookViewModel.booksLiveData.observe(viewLifecycleOwner, Observer {
            bookAdapter.updateData(it)
        })

        loadMoreItem(rootView, bookViewModel, catalogId)

        bookAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View, any: Any) {
                bookViewModel.bookDetailLiveData.postValue(any as Book)
                (activity as BookListActivity).switchFragment((activity as BookListActivity).bookDetailFragment)
            }
        })
        bookViewModel.getBookList(catalogId)
        return rootView
    }

    private fun loadMoreItem(
        rootView: View,
        bookViewModel: BookListViewModel,
        catalogId: Int
    ) {
        val refreshLayout = rootView.refreshLayout as RefreshLayout
        refreshLayout.setOnLoadMoreListener {
            bookViewModel.loadMoreBookList(catalogId, ++pageIndex)
            it.finishLoadMore()
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        if(!hidden) {
            (activity as BookListActivity).modifyCurrentFragment(this)
        }
    }
}