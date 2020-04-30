package com.thoughworks.bookrecommendation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.thoughworks.bookrecommendation.R
import com.thoughworks.bookrecommendation.http.ErrorHandler

class MainActivity : AppCompatActivity() {

    companion object {
        //        设置全局refresh Header,Footer
        init {
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white)
                ClassicsHeader(context)
            }

            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
                ClassicsFooter(context).setDrawableSize(20.toFloat())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ErrorHandler.initRxErrorHandler()

        val catalogFragment = CatalogFragment()

        switchFragment(catalogFragment)
    }

    private var currentFragment = Fragment()

    private fun switchFragment(targetFragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()

        if (!targetFragment.isAdded) {
            transaction.hide(currentFragment)
                .add(R.id.fragment, targetFragment)
                .commit()
        } else {
            transaction.hide(currentFragment)
                .show(targetFragment)
                .commit()
        }
        currentFragment = targetFragment
    }

}
