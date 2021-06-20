package com.daanidev.galleryview.ui

import com.daanidev.galleryview.network.RetrofitInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class GalleryRepository(val retrofitInterface: RetrofitInterface) {


     fun getImagesList(pageNo:Int) = flow{

             emit(retrofitInterface.getImagesList(pageNo))
         }


}