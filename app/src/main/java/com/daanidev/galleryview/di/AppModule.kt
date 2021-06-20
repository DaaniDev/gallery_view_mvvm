package com.daanidev.galleryview.di

import com.daanidev.galleryview.network.networkModule
import com.daanidev.galleryview.ui.galleryview.galleryModule

val appModule= listOf(networkModule, galleryModule)