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
import com.thoughworks.bookrecommendation.R
import com.thoughworks.bookrecommendation.viewmodel.CatalogViewModel

class CatalogFragment : Fragment() {
    private val catalogAdaptor by lazy { CatalogAdaptor() }

    private lateinit var catalogView :RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(R.layout.catalog_fragment,container,false)

        val catalogViewModel = CatalogViewModel()
        catalogViewModel.catalogLiveData.observe(viewLifecycleOwner, Observer { catalogs ->
            catalogAdaptor.updateData(catalogs)
        })

        catalogView = rootView.findViewById<RecyclerView>(R.id.catalog_view)
        catalogView.layoutManager = GridLayoutManager(context,2)

//        val catalogBuilding = DataBindingUtil.inflate<CatalogItemBinding>(
//            inflater,
//            R.layout.catalog_item,
//            container,
//            false
//        )

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

        catalogView.adapter = catalogAdaptor

        catalogViewModel.getCatalogList()
        return rootView
    }
}