package com.iyiyasa.android.ui.home.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.iyiyasa.android.R
import com.iyiyasa.android.base.bottomsheet.BaseBottomSheetDialog
import com.iyiyasa.android.base.viewmodel.BaseViewModel
import com.iyiyasa.android.data.persistence.entity.Data
import com.iyiyasa.android.databinding.FragmentAddBarcodeBinding
import com.iyiyasa.android.extensions.*
import com.iyiyasa.android.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*
import kotlin.random.Random

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
            dataBinding.edProductname.setText(it.productName)
            dataBinding.edProductdate.setText(it.productDate)
        }

    }

    fun closeBottomSheet(){
        dismissAllowingStateLoss()
    }

    fun addProduct(){
        listener.clickAddProduct(
            Data(
                Random.nextInt(),
                viewModel.barcode.get() ?: "",
                viewModel.name.get() ?: "",
                viewModel.date.get() ?: ""
            )
        )
        dismissAllowingStateLoss()
    }

    @SuppressLint("SetTextI18n")
    fun clickDate(){

        var dateListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

            startCalendar.set(Calendar.YEAR, year)
            startCalendar.set(Calendar.MONTH, month)
            startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            startCalendar.set(Calendar.HOUR_OF_DAY, System.currentTimeMillis().hourOfDay)
            startCalendar.set(Calendar.MINUTE, System.currentTimeMillis().minuteOfHour)

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

    interface ListenerAddProduct {
        fun clickAddProduct(item: Data)
    }
}