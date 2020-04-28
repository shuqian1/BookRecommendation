package com.thoughworks.bookrecommendation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.thoughworks.bookrecommendation.R
import com.thoughworks.bookrecommendation.databinding.CatalogFragmentBinding
import com.thoughworks.bookrecommendation.viewmodel.CatalogViewModel

class CatalogFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val catalogViewModel = CatalogViewModel()

//        val catalogBuilding = DataBindingUtil.setContentView<ActivityMainBinding>(,R.layout.activity_main)
        val catalogBuilding = DataBindingUtil.inflate<CatalogFragmentBinding>(
            inflater,
            R.layout.catalog_fragment,
            container,
            false
        )
        catalogBuilding.catalog = catalogViewModel

        catalogBuilding.lifecycleOwner = this
        catalogViewModel.getCatalogList()
        return catalogBuilding.root
//        return inflater.inflate(R.layout.catalog_fragment, container, false)
    }
}