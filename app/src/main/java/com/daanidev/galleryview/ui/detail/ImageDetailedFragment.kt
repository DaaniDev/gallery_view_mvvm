package com.daanidev.galleryview.ui.detail

import android.Manifest
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.daanidev.galleryview.R
import com.daanidev.galleryview.databinding.FragmentImageDetailBinding
import com.daanidev.galleryview.ui.BaseFragment
import com.daanidev.galleryview.ui.MainActivity
import com.daanidev.galleryview.ui.detail.DetailViewModel
import com.daanidev.galleryview.utils.AppConstants
import com.daanidev.galleryview.utils.AppUtils
import com.daanidev.galleryview.utils.PermissionListener
import com.fangxu.allangleexpandablebutton.ButtonData
import com.fangxu.allangleexpandablebutton.ButtonEventListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList

class ImageDetailedFragment : BaseFragment<FragmentImageDetailBinding>(),PermissionListener{

    private val detailViewModel by viewModel<DetailViewModel>()

    private val args by navArgs<ImageDetailedFragmentArgs>()
    private var downloadCheck=false
    var drawable = intArrayOf(
        R.drawable.icon_magic,
        R.drawable.icon_download,
        R.drawable.icon_share
    )
    var color = intArrayOf(
        R.color.colorButtonDetail,
        R.color.colorButtonDetail,
        R.color.colorButtonDetail,
        R.color.colorButtonDetail,
        R.color.colorButtonDetail
    )
    val buttonDatas: MutableList<ButtonData> = ArrayList()
    override fun setLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentImageDetailBinding {

        return DataBindingUtil.inflate(inflater, R.layout.fragment_image_detail, container, false)
    }

    override fun performBindings() {

        for (i in drawable.indices)
        {
            val buttonData = ButtonData.buildIconButton(context, drawable[i], 11f)
            buttonData.setBackgroundColorId(context, color[i])
            buttonDatas.add(buttonData)
        }
        binding.btnExpandable.buttonDatas = buttonDatas
        binding.btnExpandable.setButtonEventListener(object : ButtonEventListener{
            override fun onButtonClicked(index: Int) {

                when(index){

                    1->{
                        downloadCheck=true
                        if(!checkPermissionForStorage())
                        {
                            requestPermissionForStorage()
                        }
                        else{
                            AppUtils.showToast(requireContext(),resources.getString(R.string.str_wait))
                            detailViewModel.downloadBitmap(args.img)

                        }
                    }
                    2->{
                        AppUtils.showToast(requireContext(),resources.getString(R.string.str_wait))
                        downloadCheck=false
                        detailViewModel.downloadBitmap(args.img)
                    }
                }
            }

            override fun onExpand() {

            }

            override fun onCollapse() {

            }


        })


        Glide.with(requireContext()).load(args.img).into(binding.ivWallpaper)

        detailViewModel.bitmapObserver.observe(this,{


            if(downloadCheck)
            {
                AppUtils.saveImage(it,requireContext())
            }
            else{
                AppUtils.shareBitmap(requireContext(),it)
            }

        })

        (activity as MainActivity).setPermissionListener(this)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun resume() {

    }

    private fun requestPermissionForStorage() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(requireContext(),
                resources.getString(R.string.str_permission_needed),
                Toast.LENGTH_LONG).show()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                AppConstants.PERMISSION_REQUEST_CODE)
        }
    }
    private fun checkPermissionForStorage(): Boolean {
        val resultStorage= ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)

        return resultStorage == PackageManager.PERMISSION_GRANTED
    }

    override fun granted() {
        AppUtils.showToast(requireContext(),resources.getString(R.string.str_wait))
        detailViewModel.downloadBitmap(args.img)
    }

    override fun denied() {
        AppUtils.showToast(requireContext(),resources.getString(R.string.str_permission_needed))
    }


}