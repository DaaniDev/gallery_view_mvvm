package com.daanidev.galleryview.ui.detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.content.FileProvider
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daanidev.galleryview.BuildConfig
import com.daanidev.galleryview.R
import com.fangxu.allangleexpandablebutton.ButtonData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.util.*


class DetailViewModel : ViewModel() {

    var bitmapObserver = MutableLiveData<Bitmap>()


    fun downloadBitmap(imgUrl: String) {
        val url = URL(imgUrl)
       CoroutineScope(Dispatchers.IO).launch {

            val bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            bitmapObserver.postValue(bitmap)
        }
    }


}