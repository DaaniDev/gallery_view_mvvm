package com.daanidev.galleryview.ui.galleryview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.daanidev.galleryview.R
import com.daanidev.galleryview.databinding.ItemGalleryBinding

class GalleryAdapter (val galleryCallback:(imgUrl:String)->Unit): RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {


     var imagesList = mutableListOf<GalleryModelItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryAdapter.ViewHolder {

        val itemGalleryBinding: ItemGalleryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_gallery, parent, false
        )
        return ViewHolder(itemGalleryBinding)
    }

    override fun getItemCount(): Int {

        return imagesList.size
    }

    override fun onBindViewHolder(holder: GalleryAdapter.ViewHolder, position: Int) {


        holder.onBind(position)

    }


    inner class ViewHolder(itemGalleryBinding: ItemGalleryBinding) :
        RecyclerView.ViewHolder(itemGalleryBinding.root) {
        val itemGalleryBinding = itemGalleryBinding


        fun onBind(pos:Int){


            itemGalleryBinding.item=imagesList[pos]
        }

        init {
            itemView.setOnClickListener {
                galleryCallback.invoke(imagesList[adapterPosition].downloadUrl)
            }
        }

    }
}