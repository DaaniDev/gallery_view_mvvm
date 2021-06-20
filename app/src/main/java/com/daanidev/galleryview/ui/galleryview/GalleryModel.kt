package com.daanidev.galleryview.ui.galleryview
import com.google.gson.annotations.SerializedName


class GalleryModel : ArrayList<GalleryModelItem>()

data class GalleryModelItem(
    @SerializedName("author")
    val author: String,
    @SerializedName("download_url")
    val downloadUrl: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
)