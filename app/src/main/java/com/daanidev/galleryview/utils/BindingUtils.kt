package com.daanidev.galleryview.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

object BindingUtils {

    @JvmStatic
    @BindingAdapter(value = ["image"])
    fun loadImage(view: ImageView, url: String?)
    {
        Glide.with(view.context)
            .load(url)
            .centerCrop()
            .override(500,500)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .thumbnail(0.25f)
            .into(view)
    }

}