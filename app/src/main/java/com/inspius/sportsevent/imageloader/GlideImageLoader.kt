package com.inspius.sportsevent.imageloader

import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideImageLoader : ImageLoader() {
    override fun load(url: String?, imageView: ImageView) {
        Glide.with(imageView).load(url).into(imageView)
    }
}