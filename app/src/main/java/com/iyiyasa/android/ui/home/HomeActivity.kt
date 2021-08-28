package com.iyiyasa.android.ui.home

import android.app.Activity
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.iyiyasa.android.R
import com.iyiyasa.android.base.activity.BaseSlideActivity
import com.iyiyasa.android.base.viewmodel.BaseViewModel
import com.iyiyasa.android.data.persistence.entity.Data
import com.iyiyasa.android.databinding.ActivityHomeBinding
import com.iyiyasa.android.extensions.handler
import com.iyiyasa.android.extensions.resString
import com.iyiyasa.android.extensions.showDialog
import com.iyiyasa.android.ui.home.adapter.ShowProductAdapter
import com.iyiyasa.android.ui.home.fragment.AddCarcodeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by hakanaksoy on 11.08.2021.
 * Loodos
 */

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeActivity : BaseSlideActivity<ActivityHomeBinding>(R.layout.activity_home),
    ShowProductAdapter.ListenerShowProductData {

    private val viewModel by viewModels<HomeViewModel>()

    override fun getBaseViewModel(): BaseViewModel = this.viewModel

    override fun bindScreen() {
        dataBinding.viewModel = viewModel
        dataBinding.activity = this
        dataBinding.listenerProduct = this

        addedProduct()
    }

    private fun addedProduct() {
        viewModel.product.observe(this, Observer {
            (dataBinding.rvCourse.adapter as? ShowProductAdapter)?.addProduct(it)
            handler(1000){
                hideLoading()
                showDialog(resString(R.string.home_added_product),true)
            }
        })
    }

    fun openBarcode() {
        openAddBarcode()
        /*
         startForResult.launch(Intent(this, BarcodeActivity::class.java))
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
         */
    }

    private fun openAddBarcode(data: Data? = null) {
        var addBarode = AddCarcodeFragment.newInstance(data,
            object : AddCarcodeFragment.ListenerAddProduct {
                override fun clickAddProduct(item: Data) {
                    showLoading()
                    viewModel.add(item)
                }
            })
        addBarode.showBottomSheet(supportFragmentManager)
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                // Handle the Intent test
            }
        }

    override fun clickProduct(item: Data) {

    }

}