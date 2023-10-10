package com.inspius.sportsevent.imageloader

import android.widget.ImageView

abstract class ImageLoader {

    companion object {
        lateinit var loader: ImageLoader
    }

    abstract fun load(url: String?, imageView: ImageView)
}