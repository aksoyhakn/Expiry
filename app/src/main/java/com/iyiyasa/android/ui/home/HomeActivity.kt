package com.iyiyasa.android.ui.home

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.iyiyasa.android.R
import com.iyiyasa.android.base.activity.BaseActivity
import com.iyiyasa.android.base.activity.BaseSlideActivity
import com.iyiyasa.android.base.viewmodel.BaseViewModel
import com.iyiyasa.android.databinding.ActivityHomeBinding
import com.iyiyasa.android.ui.barcode.BarcodeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by hakanaksoy on 11.08.2021.
 * Loodos
 */

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeActivity : BaseSlideActivity<ActivityHomeBinding>(R.layout.activity_home) {

    private val viewModel by viewModels<HomeViewModel>()

    override fun getBaseViewModel(): BaseViewModel = this.viewModel

    override fun bindScreen() {
        dataBinding.viewModel = viewModel
        dataBinding.activity=this
    }

    fun openBarcode() {
        startForResult.launch(Intent(this, BarcodeActivity::class.java))
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                // Handle the Intent
            }
        }

}