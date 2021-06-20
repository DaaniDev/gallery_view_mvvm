package com.daanidev.galleryview.ui.galleryview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daanidev.galleryview.ui.GalleryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class GalleryViewModel(val repository: GalleryRepository) : ViewModel() {



    fun getImages(pageNo:Int) = repository.getImagesList(pageNo)

}