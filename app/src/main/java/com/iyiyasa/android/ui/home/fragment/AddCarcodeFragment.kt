package com.iyiyasa.android.ui.home.fragment

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.iyiyasa.android.R
import com.iyiyasa.android.base.bottomsheet.BaseBottomSheetDialog
import com.iyiyasa.android.base.viewmodel.BaseViewModel
import com.iyiyasa.android.databinding.FragmentAddBarcodeBinding
import com.iyiyasa.android.extensions.notNull
import com.iyiyasa.android.extensions.setBottomSheetState
import com.iyiyasa.android.ui.barcode.model.BarcodeResponse
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
            prroductBarcode: BarcodeResponse?,
            listener: ListenerAddProduct
        ): AddCarcodeFragment {
            val fragment = AddCarcodeFragment(listener)
            val bundle = bundleOf(
                Constants.AddProduct.ADD_PRODUCT to prroductBarcode,
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

        (arguments?.getParcelable<BarcodeResponse>(Constants.AddProduct.ADD_PRODUCT)).notNull {
            dataBinding.edBarcode.setText(it.dc_BarcodeId)
            dataBinding.edProduct.setText(it.dc_ProductName)
        }

    }

    fun addProduct(){
        listener.clickAddProduct(BarcodeResponse(Random.nextInt(), "1", "2", "3"))
        dismissAllowingStateLoss()
    }

    interface ListenerAddProduct {
        fun clickAddProduct(item: BarcodeResponse)
    }
}