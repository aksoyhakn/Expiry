package com.iyiyasa.android.ui.barcode

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.iyiyasa.android.base.viewmodel.BaseViewModel
import com.iyiyasa.android.data.service.util.State
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by hakanaksoy on 11.08.2021.
 * Loodos
 */

class BarcodeViewModel @ViewModelInject constructor(
    private val barcodeRepository: BarcodeRepository
) : BaseViewModel() {

    fun getInit(
        barcodeID: String
    ) {
        viewModelScope.launch {
            barcodeRepository.getBarcodeDataById(
                "5449000011114"
            ).collect {
                when (it) {
                    is State.Loading -> { }
                    is State.Success -> { }
                    is State.Fail -> { }
                    is State.Error -> {
                        activityErrorOrFail(it.throwable)
                    }
                }
            }
        }
    }

}