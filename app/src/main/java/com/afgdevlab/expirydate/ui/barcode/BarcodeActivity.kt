package com.afgdevlab.expirydate.ui.barcode

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.viewModels
import com.afgdevlab.expirydate.R
import com.afgdevlab.expirydate.base.activity.BaseSlideActivity
import com.afgdevlab.expirydate.base.viewmodel.BaseViewModel
import com.afgdevlab.expirydate.databinding.ActivityBarcodeBinding
import com.afgdevlab.expirydate.extensions.notNull
import com.afgdevlab.expirydate.utils.Constants
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.zxing.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.dm7.barcodescanner.zxing.ZXingScannerView

/**
 * Created by hakanaksoy on 11.08.2021.
 * Loodos
 */

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class BarcodeActivity : BaseSlideActivity<ActivityBarcodeBinding>(R.layout.activity_barcode),
    ZXingScannerView.ResultHandler {

    private lateinit var mScannerView: ZXingScannerView

    private val viewModel by viewModels<BarcodeViewModel>()

    override fun getBaseViewModel(): BaseViewModel = this.viewModel

    override fun bindScreen() {
        dataBinding.viewModel = viewModel
        initScannerView()
    }

    private fun initScannerView() {
        mScannerView = ZXingScannerView(this)
        mScannerView.setAutoFocus(true)
        mScannerView.setResultHandler(this)
        dataBinding.frameLayoutCamera.addView(mScannerView)

        listenerBarcode()

        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        dataBinding.adView.loadAd(adRequest)

    }

    fun listenerBarcode(){
        viewModel.product.observe(this, {product->
            val data = Intent().apply {
                putExtra(Constants.Barcode.BARCODE_PRODUCT, product)
            }
            setResult(Activity.RESULT_OK, data)
            finish()
        })

        viewModel.productError.observe(this, {product->
            val data = Intent().apply {
                putExtra(Constants.Barcode.BARCODE_ERROR, product)
            }
            setResult(Activity.RESULT_OK, data)
            finish()
        })
    }

    override fun onStart() {
        mScannerView.startCamera()
        super.onStart()
    }

    override fun onPause() {
        mScannerView.stopCamera()
        super.onPause()
    }

    override fun handleResult(rawResult: Result?) {
        rawResult?.text.notNull {
            viewModel.getInit(it)
        }
    }
}