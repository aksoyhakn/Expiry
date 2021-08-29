package com.afgdevlab.expirydate.ui.splash

import androidx.activity.viewModels
import com.afgdevlab.expirydate.R
import com.afgdevlab.expirydate.base.activity.BaseActivity
import com.afgdevlab.expirydate.base.viewmodel.BaseViewModel
import com.afgdevlab.expirydate.data.persistence.AppDatabase
import com.afgdevlab.expirydate.data.persistence.IyiyasaDao
import com.afgdevlab.expirydate.data.persistence.entity.Data
import com.afgdevlab.expirydate.databinding.ActivitySplashBinding
import com.afgdevlab.expirydate.extensions.handler
import com.afgdevlab.expirydate.extensions.isCameraPermission
import com.afgdevlab.expirydate.extensions.requestCameraPermission
import com.afgdevlab.expirydate.ui.barcode.BarcodeActivity
import com.afgdevlab.expirydate.ui.home.HomeActivity
import com.afgdevlab.expirydate.utils.transition.ActivityTransition
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import pub.devrel.easypermissions.EasyPermissions

/**
 * Created by hakanaksoy on 11.08.2021.
 * Loodos
 */

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash),
    EasyPermissions.PermissionCallbacks {

    private val viewModel by viewModels<SplashViewModel>()

    override fun getBaseViewModel(): BaseViewModel = this.viewModel

    override fun bindScreen() {
        dataBinding.viewModel = viewModel

        handler(2000) {
            if (isCameraPermission()) {
                openHome()
            } else {
                requestCameraPermission()
            }
        }
    }

    fun openHome() {
        ActivityTransition.Builder(this, HomeActivity::class.java)
            .setData(intent.data)
            .setClearTask(true)
            .build()
            .start()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        finish()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        openHome()
    }

}