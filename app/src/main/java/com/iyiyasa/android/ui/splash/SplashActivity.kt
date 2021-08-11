package com.iyiyasa.android.ui.splash

import androidx.activity.viewModels
import com.iyiyasa.android.R
import com.iyiyasa.android.base.activity.BaseActivity
import com.iyiyasa.android.base.viewmodel.BaseViewModel
import com.iyiyasa.android.data.preference.PreferenceHelperImp
import com.iyiyasa.android.databinding.ActivitySplashBinding
import com.iyiyasa.android.extensions.handler
import com.iyiyasa.android.utils.analytics.SCREEN
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val viewModel by viewModels<SplashViewModel>()

    override fun getBaseViewModel(): BaseViewModel = this.viewModel

    override fun bindScreen() {
        dataBinding.viewModel = viewModel
    }
}