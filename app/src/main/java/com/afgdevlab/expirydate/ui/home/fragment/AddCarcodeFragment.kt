package com.afgdevlab.expirydate.ui.home.fragment

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.afgdevlab.expirydate.R
import com.afgdevlab.expirydate.base.bottomsheet.BaseBottomSheetDialog
import com.afgdevlab.expirydate.base.viewmodel.BaseViewModel
import com.afgdevlab.expirydate.data.persistence.entity.Data
import com.afgdevlab.expirydate.databinding.FragmentAddBarcodeBinding
import com.afgdevlab.expirydate.extensions.*
import com.afgdevlab.expirydate.utils.Constants
import com.afgdevlab.expirydate.utils.notification.AlarmReceiver
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*


/**
 * Created by hakanaksoy on 21.06.2021.
 * Loodos
 */

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AddCarcodeFragment(
    var listener: ListenerAddProduct
) : BaseBottomSheetDialog<FragmentAddBarcodeBinding>(R.layout.fragment_add_barcode) {

    var startCalendar: Calendar = Calendar.getInstance()

    companion object {
        fun newInstance(
            product: Data?,
            listener: ListenerAddProduct
        ): AddCarcodeFragment {
            val fragment = AddCarcodeFragment(listener)
            val bundle = bundleOf(
                Constants.AddProduct.ADD_PRODUCT to product,
            )
            fragment.arguments = bundle
            return fragment
        }
    }

    private val viewModel by viewModels<AddCarcodeFragmentViewModel>()

    override fun getBaseViewModel(): BaseViewModel = this.viewModel

    override fun bindScreen() {
        dataBinding.viewModel = viewModel
        dataBinding.fragment = this

        setBottomSheetState(BottomSheetBehavior.STATE_EXPANDED)

        (arguments?.getParcelable<Data>(Constants.AddProduct.ADD_PRODUCT)).notNull {
            dataBinding.edProductBarcode.setText(it.productBarcodeID)
            viewModel.barcode.set(it.productBarcodeID)

            dataBinding.edProductname.setText(it.productName)
            viewModel.name.set(it.productName)
        }

    }

    fun closeBottomSheet(){
        dismissAllowingStateLoss()
    }

    fun addProduct(){

        if (!viewModel.barcode.get().isNullOrEmpty() &&
            !viewModel.name.get().isNullOrEmpty() &&
            !viewModel.date.get().isNullOrEmpty()
        ) {
            var uniqeID = (0..100000).random()
            startAlarm(startCalendar,uniqeID)
            listener.clickAddProduct(
                Data(
                    uniqeID,
                    viewModel.barcode.get() ?: "",
                    viewModel.name.get() ?: "",
                    viewModel.date.get() ?: "",
                    viewModel.lastDateControl.get() ?: "4"
                )
            )
            dismissAllowingStateLoss()
        }
    }

    @SuppressLint("SetTextI18n")
    fun clickDate(){

        var dateListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

            startCalendar.set(Calendar.YEAR, year)
            startCalendar.set(Calendar.MONTH, month)
            startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            startCalendar.set(Calendar.HOUR_OF_DAY, System.currentTimeMillis().hourOfDay)
            startCalendar.set(Calendar.MINUTE, System.currentTimeMillis().minuteOfHour+2)

            viewModel.lastDateControl.set((startCalendar.time.time - System.currentTimeMillis()).dayOfMonth.toString())

            dataBinding.edProductdate.setText(
                "${
                    String.format(
                        "%02d",
                        startCalendar.time.time.dayOfMonth
                    )
                } ${startCalendar.time.time.monthOfName} $year"
            )

        }

        var picker = DatePickerDialog(
            requireContext(), R.style.datepicker, dateListener,
            startCalendar.get(Calendar.YEAR),
            startCalendar.get(Calendar.MONTH),
            startCalendar.get(Calendar.DAY_OF_MONTH)
        )

        picker.datePicker.minDate = System.currentTimeMillis()
        picker.show()
    }

    private fun startAlarm(calendar: Calendar,uniqeID:Int) {
        viewModel.addNotificationChannel(calendar.time.getNotificationTimeStamp())
        val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireActivity(), AlarmReceiver::class.java)

        intent.putExtra(
            Constants.Notification.NOTIFICATION_CHANNEL_ID,
            calendar.time.getNotificationTimeStamp()
        )
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), uniqeID, intent, 0)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }


    interface ListenerAddProduct {
        fun clickAddProduct(item: Data)
    }
}