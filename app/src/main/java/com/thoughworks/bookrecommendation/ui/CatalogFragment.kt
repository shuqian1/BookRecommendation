package com.thoughworks.bookrecommendation.ui

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.thoughworks.bookrecommendation.R
import com.thoughworks.bookrecommendation.viewmodel.CatalogViewModel

class CatalogFragment : Fragment() {
    private val catalogAdaptor by lazy { CatalogAdaptor() }

    private lateinit var catalogView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(R.layout.catalog_fragment, container, false)

        val catalogViewModel = CatalogViewModel(requireContext())
        catalogViewModel.catalogLiveData.observe(viewLifecycleOwner, Observer { catalogs ->
            catalogAdaptor.updateData(catalogs)
        })

        catalogView = rootView.findViewById<RecyclerView>(R.id.catalog_view)
        catalogView.layoutManager = GridLayoutManager(context, 2)

        catalogView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.bottom += 8
            }
        })

        catalogAdaptor.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View, any: Any) {
                var bundle = Bundle()
                bundle.putString("id",any.toString())
                val catalogFragment=CatalogFragment()
                catalogFragment.arguments = bundle
                println("id:"+any.toString())
//                TODO switch fragment
            }
        })

        catalogView.adapter = catalogAdaptor

        val refreshLayout = rootView.findViewById<View>(R.id.refreshLayout) as RefreshLayout
        refreshLayout.setOnRefreshListener { layout ->
            catalogViewModel.getCatalogList()
            layout.finishRefresh()
        }
        refreshLayout.setEnableLoadMore(false)

        catalogViewModel.getCatalogList()
        return rootView
    }
}