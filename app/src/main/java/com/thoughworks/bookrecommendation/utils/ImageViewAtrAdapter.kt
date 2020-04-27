package com.thoughworks.bookrecommendation.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.thoughworks.bookrecommendation.R

object ImageViewAtrAdapter {

    @JvmStatic
    @BindingAdapter("remote")
    fun loadImage(imageView: ImageView, url: String?) {
        if (url != null) {
            Glide.with(imageView.context)
                .applyDefaultRequestOptions(RequestOptions().placeholder(R.mipmap.ic_launcher))
                .load(url).into(imageView)
        }
    }
}