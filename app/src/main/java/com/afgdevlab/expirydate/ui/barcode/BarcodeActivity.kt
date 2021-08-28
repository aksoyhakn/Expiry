package com.afgdevlab.expirydate.ui.barcode

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.viewModels
import com.google.zxing.Result
import com.afgdevlab.expirydate.R
import com.afgdevlab.expirydate.base.activity.BaseSlideActivity
import com.afgdevlab.expirydate.base.viewmodel.BaseViewModel
import com.afgdevlab.expirydate.databinding.ActivityBarcodeBinding
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
        viewModel.getInit("")
    }

    private fun initScannerView() {
        mScannerView = ZXingScannerView(this)
        mScannerView.setAutoFocus(true)
        mScannerView.setResultHandler(this)
        dataBinding.frameLayoutCamera.addView(mScannerView)
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
        Log.d("TEST",rawResult?.text.toString())
      /*  val data = Intent().apply {
            putExtra(RESULT_ID, id)
            putExtra(RESULT_NAME, name)
        }
        setResult(RESULT_OK, data)*/
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}