package com.daanidev.galleryview.ui.galleryview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.daanidev.galleryview.R
import com.daanidev.galleryview.databinding.FragmentGalleryBinding
import com.daanidev.galleryview.ui.BaseFragment
import com.daanidev.galleryview.ui.MainActivity
import com.daanidev.galleryview.utils.AppConstants
import com.daanidev.galleryview.utils.RecyclerViewDecoration
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class GalleryFragment : BaseFragment<FragmentGalleryBinding>(),SwipeRefreshLayout.OnRefreshListener{

    private var isLoading=false
    private var pageNo:Int?=null
    private lateinit var galleryAdapter: GalleryAdapter
    private val galleryViewModel by viewModel<GalleryViewModel>()
    override fun setLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGalleryBinding {
        return DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false)
    }

    override fun performBindings() {

        pageNo=1
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

            galleryAdapter = GalleryAdapter {str->

               findNavController().navigate(
                    GalleryFragmentDirections.actionGalleryFragmentToImageDetailedFragment(
                        str
                    )
                )
        }

        binding.listGallery.addItemDecoration(RecyclerViewDecoration(requireContext()))
        binding.listGallery.layoutManager=StaggeredGridLayoutManager(
            2,
            StaggeredGridLayoutManager.VERTICAL
        )

        binding.listGallery.adapter=galleryAdapter
        binding.swipeLayout.setOnRefreshListener(this)


        binding.listGallery.addOnScrollListener(object : RecyclerView.OnScrollListener() {


            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItem = recyclerView.layoutManager?.itemCount
                val lastVisibleItem = (recyclerView.layoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null).last()

                totalItem?.let {

                    if (!isLoading && lastVisibleItem ==it.minus(1)) {

                      pageNo=it.div(AppConstants.PAGE_SIZE).plus(1)
                        getImageList()
                    }
                }


            }
        })


            getImageList()


    }
    override fun resume() {

    }


    private fun getImageList(){
        isLoading=true
        binding.swipeLayout.isRefreshing=true
        lifecycleScope.launch {


            galleryViewModel.getImages(pageNo!!).collect {

                if(it.isSuccessful)
                {
                    isLoading=false
                    binding.listGallery.visibility= View.VISIBLE
                    val totalItem= galleryAdapter.itemCount
                    galleryAdapter.imagesList.addAll(it.body()!!)
                    binding.swipeLayout.isRefreshing=false
                    galleryAdapter.notifyItemRangeInserted(totalItem,galleryAdapter.itemCount)
                }
            }
        }

    }

    override fun onRefresh() {

        galleryAdapter.let {

            it.imagesList.clear()
            pageNo=1
            it.notifyDataSetChanged()
            getImageList()
        }
    }
}