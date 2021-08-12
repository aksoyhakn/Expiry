package com.iyiyasa.android.ui.splash

import androidx.activity.viewModels
import com.iyiyasa.android.R
import com.iyiyasa.android.base.activity.BaseActivity
import com.iyiyasa.android.base.viewmodel.BaseViewModel
import com.iyiyasa.android.data.persistence.AppDatabase
import com.iyiyasa.android.data.persistence.IyiyasaDao
import com.iyiyasa.android.data.persistence.entity.Data
import com.iyiyasa.android.databinding.ActivitySplashBinding
import com.iyiyasa.android.extensions.handler
import com.iyiyasa.android.extensions.isCameraPermission
import com.iyiyasa.android.extensions.requestCameraPermission
import com.iyiyasa.android.ui.barcode.BarcodeActivity
import com.iyiyasa.android.utils.transition.ActivityTransition
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
        ActivityTransition.Builder(this, BarcodeActivity::class.java)
            .setData(intent.data)
            .setClearTask(true)
            .build()
            .start()


        /*

        var database : AppDatabase = AppDatabase.getDatabase(this)!!
        database.iyiyasaDAO().insertUser(Data(null,"5449000011114","COCA COLA 200ML"))
        database.iyiyasaDAO().insertUser(Data(7343,"5449000011114","COCA COLA 200ML"))
        database.iyiyasaDAO().insertUser(Data(null,"5449000011114","COCA COLA 200ML"))
        database.iyiyasaDAO().getUsers()

         */
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