package com.afgdevlab.expirydate.ui.home

import android.app.Activity
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.afgdevlab.expirydate.R
import com.afgdevlab.expirydate.base.activity.BaseSlideActivity
import com.afgdevlab.expirydate.base.viewmodel.BaseViewModel
import com.afgdevlab.expirydate.data.persistence.entity.Data
import com.afgdevlab.expirydate.databinding.ActivityHomeBinding
import com.afgdevlab.expirydate.extensions.*
import com.afgdevlab.expirydate.ui.home.adapter.ShowProductAdapter
import com.afgdevlab.expirydate.ui.home.fragment.AddCarcodeFragment
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

        listenerProduct()
    }

    private fun listenerProduct() {
        viewModel.product.observe(this, Observer {
            (dataBinding.rvCourse.adapter as? ShowProductAdapter)?.addProduct(it)
            handler(1000) {
                hideLoading()
                showDialog(resString(R.string.home_added_product), true)
            }
        })

        viewModel.productDelete.observe(this, Observer {
            handler(1000) {
                hideLoading()
                showDialog(resString(R.string.home_delete_product), true)
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

    override fun clickProduct(item: Data, position: Int) {
        showProductStatus {
            item.notNull {
                showLoading()
                viewModel.update(it)
                handler(1000) {
                    hideLoading()
                    (dataBinding.rvCourse.adapter as? ShowProductAdapter)?.updateProduct(
                        position,
                        it
                    )
                }
            }
        }
    }

    override fun clickProductDelete(item: Data) {
        showLoading()
        viewModel.delete(item)
    }

}