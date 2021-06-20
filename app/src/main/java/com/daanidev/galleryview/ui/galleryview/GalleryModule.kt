package com.daanidev.galleryview.ui.galleryview

import com.daanidev.galleryview.ui.GalleryRepository
import com.daanidev.galleryview.ui.detail.DetailViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val galleryModule= module {


    viewModel {
        GalleryViewModel(get())
    }

    viewModel {
        DetailViewModel()
    }

    single {

        GalleryRepository(get())
    }
}