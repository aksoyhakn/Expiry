package com.iyiyasa.android.ui.home.fragment

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.iyiyasa.android.R
import com.iyiyasa.android.base.bottomsheet.BaseBottomSheetDialog
import com.iyiyasa.android.base.viewmodel.BaseViewModel
import com.iyiyasa.android.data.persistence.entity.Data
import com.iyiyasa.android.databinding.FragmentAddBarcodeBinding
import com.iyiyasa.android.extensions.notNull
import com.iyiyasa.android.extensions.setBottomSheetState
import com.iyiyasa.android.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    interface ListenerAddProduct {
        fun clickAddProduct(item: Data)
    }
}