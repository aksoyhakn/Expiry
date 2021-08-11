package com.iyiyasa.android.ui.barcode

import androidx.activity.viewModels
import com.iyiyasa.android.R
import com.iyiyasa.android.base.activity.BaseActivity
import com.iyiyasa.android.base.activity.BaseSlideActivity
import com.iyiyasa.android.base.viewmodel.BaseViewModel
import com.iyiyasa.android.databinding.ActivityBarcodeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class BarcodeActivity : BaseSlideActivity<ActivityBarcodeBinding>(R.layout.activity_barcode) {

    private val viewModel by viewModels<BarcodeViewModel>()

    override fun getBaseViewModel(): BaseViewModel = this.viewModel

    override fun bindScreen() {
        dataBinding.viewModel = viewModel
    }
}