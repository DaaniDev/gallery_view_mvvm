package com.daanidev.galleryview.utils

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.FileProvider
import com.daanidev.galleryview.BuildConfig
import com.daanidev.galleryview.R
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

object AppUtils {


    fun shareBitmap(context: Context, bitmap: Bitmap) {
        try {
            val file = File(context.externalCacheDir, "share.png")
            val fOut = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut)
            fOut.flush()
            fOut.close()
            @SuppressLint("SetWorldReadable") val readable = file.setReadable(true, false)
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(
                Intent.EXTRA_STREAM,
                FileProvider.getUriForFile(
                    context,
                    BuildConfig.APPLICATION_ID + ".provider",
                    file
                )
            )
            intent.type = "image/png"
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(Intent.createChooser(intent, "Share image via"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun saveImage(bitmap: Bitmap?, context: Context) {

        val fileName = System.currentTimeMillis().toString() + ".jpg"
        var fos: OutputStream? = null
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            context.contentResolver?.also { resolver ->
                val directory = File(
                    Environment.DIRECTORY_PICTURES + File.separator + AppConstants.FOLDER_NAME
                )

                if (!directory.exists()) {
                    directory.mkdirs()
                }
                //Content resolver will process the contentvalues
                val contentValues = ContentValues().apply {

                    //putting file information in content values
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(
                        MediaStore.MediaColumns.RELATIVE_PATH,
                        Environment.DIRECTORY_PICTURES + File.separator + AppConstants.FOLDER_NAME
                    )
                }

                //Inserting the contentValues to contentResolver and getting the Uri
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                //Opening an outputstream with the Uri that we got
                fos = imageUri?.let { resolver.openOutputStream(it) }
                fos?.use {
                    //Finally writing the bitmap to the output stream that we opened
                    bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, it)

                }


            }
            showToast(context,context.resources.getString(R.string.str_image_saved))
        } else {

            val directory = File(
                Environment.getExternalStorageDirectory()
                    .toString() + File.separator + AppConstants.FOLDER_NAME
            )
            // getExternalStorageDirectory is deprecated in API 29

            if (!directory.exists()) {
                directory.mkdirs()
            }

            val file = File(directory, fileName)
            bitmap?.let { saveImageToStream(it, FileOutputStream(file), context) }
            val values = contentValues()
            values.put(MediaStore.Images.Media.DATA, file.absolutePath)
            // .DATA is deprecated in API 29
            context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        }

    }
    private fun contentValues(): ContentValues {
        val values = ContentValues()
        values.put(
            MediaStore.MediaColumns.DISPLAY_NAME, System.currentTimeMillis()
        )
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
        }
        return values
    }

    private fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream?, context: Context) {
        if (outputStream != null) {
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.close()
             showToast(context, context.resources.getString(R.string.str_image_saved))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun showToast(context: Context,msg:String){

        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
    }

}