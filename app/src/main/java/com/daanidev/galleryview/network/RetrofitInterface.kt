package com.daanidev.galleryview.network

import com.daanidev.galleryview.ui.galleryview.GalleryModel
import com.daanidev.galleryview.utils.AppConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {

    @GET("list")
    suspend fun getImagesList(@Query("page") page_no:Int,@Query("limit") page_limit:Int=AppConstants.PAGE_SIZE) : Response<GalleryModel>
}