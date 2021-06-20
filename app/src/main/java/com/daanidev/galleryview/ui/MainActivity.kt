package com.daanidev.galleryview.ui

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.daanidev.galleryview.R
import com.daanidev.galleryview.utils.AppConstants
import com.daanidev.galleryview.utils.AppUtils
import com.daanidev.galleryview.utils.PermissionListener

class MainActivity : AppCompatActivity() {


    private lateinit var permissionListener: PermissionListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph=navController.navInflater.inflate(R.navigation.nav_graph)
        navController.graph=navGraph

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
      onBackPressed()
            return true
        }
        return false
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == AppConstants.PERMISSION_REQUEST_CODE) {
            var permissionGranted = true

            for (grantResult in grantResults) {
                permissionGranted = permissionGranted and
                        (grantResult == PackageManager.PERMISSION_GRANTED)
            }

            if (permissionGranted) {
            permissionListener.granted()
            } else {
                permissionListener.denied()

            }
        }
    }

    fun setPermissionListener(permissionListener: PermissionListener){

        this.permissionListener=permissionListener
    }
}



