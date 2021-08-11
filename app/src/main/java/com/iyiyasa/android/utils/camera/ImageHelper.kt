package com.iyiyasa.android.utils.camera

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.widget.ImageView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.iyiyasa.android.R
import com.iyiyasa.android.utils.Constants.Camera.CAPTURE_VIDEO
import com.iyiyasa.android.utils.Constants.Camera.PICK_PICTURE_FROM_CHOOSER
import com.iyiyasa.android.utils.Constants.Camera.PICK_PICTURE_FROM_DOCUMENTS
import com.iyiyasa.android.utils.Constants.Camera.PICK_PICTURE_FROM_GALLERY
import com.iyiyasa.android.utils.Constants.Camera.TAKE_PICTURE
import com.yalantis.ucrop.UCrop
import okhttp3.MediaType
import okhttp3.RequestBody
import pl.aprilapps.easyphotopicker.ChooserType
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.MediaFile
import pl.aprilapps.easyphotopicker.MediaSource
import retrofit2.Callback
import java.io.ByteArrayOutputStream
import java.io.File

class ImageHelper {

    companion object {
        private const val FOLDER_NAME = "Sweaters"
        private const val SAMPLE_CROPPED_IMAGE_NAME = "SweaterCropImage"

        fun isEasyImageRequest(requestCode: Int): Boolean {
            return requestCode == PICK_PICTURE_FROM_DOCUMENTS || requestCode == PICK_PICTURE_FROM_GALLERY || requestCode == PICK_PICTURE_FROM_CHOOSER || requestCode == TAKE_PICTURE || requestCode == CAPTURE_VIDEO
        }
    }

    private var mDestinationUri: Uri? = null
    private var fragment: Fragment? = null
    private var activity: Activity? = null
    private var onImageCropped: ((uri: Uri) -> Unit)? = null
    private var onImagesTaken: ((imageFiles: Array<MediaFile>) -> Unit)? = null

    private var easyImage: EasyImage? = null

    fun init(activity: Activity, fragment: Fragment? = null, onImageCropped: (uri: Uri) -> Unit) {
        this.fragment = fragment
        this.activity = activity
        this.mDestinationUri = Uri.fromFile(File(activity.cacheDir, SAMPLE_CROPPED_IMAGE_NAME))
        this.onImageCropped = onImageCropped
    }

    fun initMultiple(
        activity: Activity,
        fragment: Fragment? = null,
        onImagesTaken: (imageFiles: Array<MediaFile>) -> Unit
    ) {
        this.fragment = fragment
        this.activity = activity
        this.mDestinationUri = Uri.fromFile(File(activity.cacheDir, SAMPLE_CROPPED_IMAGE_NAME))
        this.onImagesTaken = onImagesTaken
    }


    fun openPhotoChooser(title: String, allowMultiple: Boolean = false) {
        setEasyImage(title, allowMultiple)

        if (fragment != null) {
            easyImage!!.openChooser(fragment!!)
        } else {
            easyImage!!.openChooser(activity!!)
        }
    }

    fun openGallery(title: String, allowMultiple: Boolean = false) {
        setEasyImage(title, allowMultiple)

        if (fragment != null) {
            easyImage!!.openGallery(fragment!!)
        } else {
            easyImage!!.openGallery(activity!!)
        }
    }

    fun openGallery(@StringRes titleRes: Int, allowMultiple: Boolean = false) {
        setEasyImage(activity!!.getString(titleRes), allowMultiple)

        if (fragment != null) {
            easyImage!!.openGallery(fragment!!)
        } else {
            easyImage!!.openGallery(activity!!)
        }
    }

    fun openCameraForImage(title: String, allowMultiple: Boolean = false) {
        setEasyImage(title, allowMultiple)

        if (fragment != null) {
            easyImage!!.openCameraForImage(fragment!!)
        } else {
            easyImage!!.openCameraForImage(activity!!)
        }
    }

    fun openCameraForImage(@StringRes titleRes: Int, allowMultiple: Boolean = false) {
        setEasyImage(activity!!.getString(titleRes), allowMultiple)

        if (fragment != null) {
            easyImage!!.openCameraForImage(fragment!!)
        } else {
            easyImage!!.openCameraForImage(activity!!)
        }
    }

    fun openCameraForVideo(title: String, allowMultiple: Boolean = false) {
        setEasyImage(title, allowMultiple)

        if (fragment != null) {
            easyImage!!.openCameraForVideo(fragment!!)
        } else {
            easyImage!!.openCameraForVideo(activity!!)
        }
    }


    fun onDestroy() {
        mDestinationUri = null
        fragment = null
        activity = null
        easyImage = null
        onImageCropped = null
    }

    fun onActivityResultWithCrop(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == UCrop.REQUEST_CROP) {
            if (data != null && data.extras != null) {
                onImageCropped?.invoke(UCrop.getOutput(data)!!)
            }
        } else {
            easyImage!!.handleActivityResult(
                requestCode,
                resultCode,
                data,
                activity!!,
                object : EasyImage.Callbacks {
                    override fun onCanceled(source: MediaSource) {
                        //Not necessary to remove any files manually anymore
                    }

                    override fun onImagePickerError(error: Throwable, source: MediaSource) {
                        error.printStackTrace()
                    }

                    override fun onMediaFilesPicked(
                        imageFiles: Array<MediaFile>,
                        source: MediaSource
                    ) {
                        if (imageFiles.size == 1 && onImagesTaken == null) {
                            galleryAddPic(imageFiles[0].file)
                            startCropActivity(Uri.fromFile(imageFiles[0].file))
                        } else {
                            onImagesTaken?.invoke(imageFiles)
                        }
                    }
                })
        }
    }


    private fun setEasyImage(title: String, allowMultiple: Boolean) {
        easyImage = EasyImage.Builder(activity!!)
            // Chooser only
            // Will appear as a system chooser title, DEFAULT empty string
            //.setChooserTitle("Pick media")
            // Will tell chooser that it should show documents or gallery apps
            //.setChooserType(ChooserType.CAMERA_AND_DOCUMENTS)  you can use this or the one below
            .setChooserType(ChooserType.CAMERA_AND_GALLERY)

            // Setting to true will cause taken pictures to show up in the device gallery, DEFAULT false
            .setCopyImagesToPublicGalleryFolder(false)
            // Sets the name for images stored if setCopyImagesToPublicGalleryFolder = true
            .setFolderName(FOLDER_NAME)
            // Allow multiple picking
            .allowMultiple(allowMultiple)
            .setChooserTitle(title)
            .build()
    }

    private fun startCropActivity(uri: Uri) {
        val uCropOptions = UCrop.Options()
        uCropOptions.setShowCropGrid(true)
        uCropOptions.setShowCropFrame(true)
        activity?.applicationContext?.let {
            uCropOptions.setCropFrameColor(
                ContextCompat.getColor(
                    it,
                    R.color.colorAccent
                )
            )

            uCropOptions.setToolbarColor(
                ContextCompat.getColor(
                    it,
                    R.color.colorAccent
                )
            )
            uCropOptions.setStatusBarColor(
                ContextCompat.getColor(
                    it,
                    R.color.colorAccent
                )
            )
        }

        if (fragment != null) {
            UCrop.of(uri, mDestinationUri!!)
                .withMaxResultSize(640, 480)
                .withOptions(uCropOptions)
                .start(activity!!, fragment!!)
        } else {
            UCrop.of(uri, mDestinationUri!!)
                .withMaxResultSize(640, 480)
                .withOptions(uCropOptions)
                .start(activity!!)
        }
    }

    private fun galleryAddPic(imageFile: File) {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val contentUri = Uri.fromFile(imageFile)
        mediaScanIntent.data = contentUri
        activity!!.sendBroadcast(mediaScanIntent)
    }









}