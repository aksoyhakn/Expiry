package com.afgdevlab.expirydate.ui.splash

import androidx.activity.viewModels
import com.afgdevlab.expirydate.R
import com.afgdevlab.expirydate.base.activity.BaseActivity
import com.afgdevlab.expirydate.base.viewmodel.BaseViewModel
import com.afgdevlab.expirydate.data.service.RemoteConfigOptions
import com.afgdevlab.expirydate.data.service.Session
import com.afgdevlab.expirydate.databinding.ActivitySplashBinding
import com.afgdevlab.expirydate.extensions.*
import com.afgdevlab.expirydate.ui.home.HomeActivity
import com.afgdevlab.expirydate.utils.transition.ActivityTransition
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.gson.Gson
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
                remoteConfig()
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

    private fun remoteConfig() {
        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val remoteConfigOptionsJson: String = remoteConfig.getString("remote_config_options")

                    if (!remoteConfigOptionsJson.isNullOrBlank()) {

                        Session.current.remoteConfigOptions =
                            Gson().fromJson(
                                remoteConfigOptionsJson,
                                RemoteConfigOptions::class.java
                            )

                        Session.current.remoteConfigOptions.notNull {
                            if(it.isForceUpdate == true){
                                showForceUpdate()
                            }else{
                                openHome()
                            }
                        }

                    }
                } else {
                    openHome()
                }
            }.addOnFailureListener {
                openHome()
            }
    }

}