package com.iyiyasa.android.ui.barcode

import androidx.hilt.lifecycle.ViewModelInject
import com.iyiyasa.android.base.viewmodel.BaseViewModel


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

class BarcodeViewModel @ViewModelInject constructor(
    private val barcodeRepository: BarcodeRepository
) : BaseViewModel() {
}