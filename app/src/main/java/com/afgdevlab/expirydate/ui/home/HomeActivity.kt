package com.afgdevlab.expirydate.ui.home

import android.app.Activity
import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.afgdevlab.expirydate.R
import com.afgdevlab.expirydate.base.activity.BaseSlideActivity
import com.afgdevlab.expirydate.base.viewmodel.BaseViewModel
import com.afgdevlab.expirydate.data.persistence.entity.Data
import com.afgdevlab.expirydate.databinding.ActivityHomeBinding
import com.afgdevlab.expirydate.extensions.*
import com.afgdevlab.expirydate.ui.barcode.BarcodeActivity
import com.afgdevlab.expirydate.ui.home.adapter.ShowProductAdapter
import com.afgdevlab.expirydate.ui.home.fragment.AddCarcodeFragment
import com.afgdevlab.expirydate.utils.Constants
import com.afgdevlab.expirydate.utils.notification.AlarmReceiver
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.appopen.AppOpenAd.load
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import dagger.hilt.android.AndroidEntryPoint
import hideKeyboard
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*


/**
 * Created by hakanaksoy on 11.08.2021.
 * Loodos
 */

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeActivity : BaseSlideActivity<ActivityHomeBinding>(R.layout.activity_home),
    ShowProductAdapter.ListenerShowProductData, RewardedVideoAdListener {

    private val viewModel by viewModels<HomeViewModel>()

    private  var mRewardedVideoAd: RewardedVideoAd ?= null


    override fun getBaseViewModel(): BaseViewModel = this.viewModel

    override fun bindScreen() {
        dataBinding.viewModel = viewModel
        dataBinding.activity = this
        dataBinding.listenerProduct = this

        listenerProduct()

        dataBinding.edSearch.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                (dataBinding.rvSearch.adapter as? ShowProductAdapter)?.filter?.filter(viewModel.searchKey.get())
                hideKeyboard()
            }
            true
        }


        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        dataBinding.adView.loadAd(adRequest)

    }

    private fun listenerProduct() {
        viewModel.product.observe(this, Observer {
            (dataBinding.rvSearch.adapter as? ShowProductAdapter)?.addProduct(it)
            viewModel.item.set(viewModel.appDatabase.iyiyasaDAO().getData() as ArrayList<Data>)
            handler(1000) {
                hideLoading()
                showDialog(resString(R.string.home_added_product), true)
            }
        })

        viewModel.productDelete.observe(this, Observer {
            viewModel.item.set(viewModel.appDatabase.iyiyasaDAO().getData() as ArrayList<Data>)
            handler(1000) {
                hideLoading()
                showDialog(resString(R.string.home_delete_product), true)
            }
        })
    }

    fun openBarcode() {
        startForResult.launch(Intent(this, BarcodeActivity::class.java))
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)

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
                var data = intent?.getParcelableExtra<Data>(Constants.Barcode.BARCODE_PRODUCT)
                var error = intent?.getStringExtra(Constants.Barcode.BARCODE_ERROR)

                data.notNull {
                    openAddBarcode(data)
                }

                error.notNull {
                    openAddBarcode(Data(productBarcodeID = it))
                }
            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun clickProduct(item: Data, position: Int) {
        showProductStatus(item.isOpenProduct ?: false,{
            item.notNull {
                showLoading()
                viewModel.update(it)
                handler(1000) {
                    hideLoading()
                    (dataBinding.rvSearch.adapter as? ShowProductAdapter)?.updateProduct(
                        position,
                        it
                    )
                }
            }
        }, {
            showLoading()

            (dataBinding.rvSearch.adapter as? ShowProductAdapter)?.deletePosition(position)

            var notificationChannelID =
                viewModel.appDatabase.iyiyasaDAO().getNotificationChannelID(item.productName!!)
            var manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.deleteNotificationChannel("${notificationChannelID.notificationChannelID}ID")

            deleteAlarm(item.ID!!)
            viewModel.delete(item)
        })
    }

    override fun clickProductSearchSize(item: Int) {
        if (item == 0) {
            dataBinding.llNotFound.visibility = View.VISIBLE
            dataBinding.rvSearch.visibility = View.GONE
        } else {
            dataBinding.llNotFound.visibility = View.GONE
            dataBinding.rvSearch.visibility = View.VISIBLE
        }
    }

    private fun deleteAlarm(uniqeID:Int) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, uniqeID, intent, 0)
        val pendingIntent1 = PendingIntent.getBroadcast(this, uniqeID+1, intent, 0)
        val pendingIntent2 = PendingIntent.getBroadcast(this, uniqeID+2, intent, 0)

        alarmManager.cancel(pendingIntent)
        alarmManager.cancel(pendingIntent1)
        alarmManager.cancel(pendingIntent2)

    }

    private fun loadRewardedVideoAd() {
        mRewardedVideoAd?.loadAd("ca-app-pub-3940256099942544/5224354917",
            AdRequest.Builder().build())
    }

    override fun onRewardedVideoAdLoaded() {}

    override fun onRewardedVideoAdOpened() {}

    override fun onRewardedVideoStarted() {}

    override fun onRewardedVideoAdClosed() {
        mRewardedVideoAd?.destroy(this)
    }

    override fun onRewarded(p0: RewardItem?) {
        mRewardedVideoAd?.destroy(this)
    }

    override fun onRewardedVideoAdLeftApplication() {}

    override fun onRewardedVideoAdFailedToLoad(p0: Int) {
        mRewardedVideoAd?.destroy(this)
    }

    override fun onRewardedVideoCompleted() {
        mRewardedVideoAd?.destroy(this)
        openBarcode()
    }

    fun openAds(){
        if (mRewardedVideoAd?.isLoaded!!) {
            mRewardedVideoAd?.show()
        }else{
            openBarcode()
        }
    }

    fun initAds(){
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this)
        mRewardedVideoAd?.rewardedVideoAdListener = this
        loadRewardedVideoAd()
    }

    override fun onPause() {
        super.onPause()
        mRewardedVideoAd?.pause(this)
    }

    override fun onResume() {
        super.onResume()
        initAds()
    }

    override fun onDestroy() {
        super.onDestroy()
        mRewardedVideoAd?.destroy(this)
    }

}