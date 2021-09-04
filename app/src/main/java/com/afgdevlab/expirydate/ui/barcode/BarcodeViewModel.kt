package com.afgdevlab.expirydate.ui.barcode

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.afgdevlab.expirydate.base.viewmodel.BaseViewModel
import com.afgdevlab.expirydate.data.persistence.entity.Data
import com.afgdevlab.expirydate.data.service.util.State
import com.afgdevlab.expirydate.extensions.notNull
import com.afgdevlab.expirydate.utils.SingleLiveData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by hakanaksoy on 11.08.2021.
 * Loodos
 */

class BarcodeViewModel @ViewModelInject constructor(
    private val barcodeRepository: BarcodeRepository
) : BaseViewModel() {

    var item = ObservableField<Data>()
    var product = SingleLiveData<Data>()
    var productError = SingleLiveData<String>()

    fun getInit(
        barcodeID: String
    ) {
        viewModelScope.launch {
            barcodeRepository.getBarcodeDataById(
                barcodeID
            ).collect {
                when (it) {
                    is State.Loading -> {
                        toogleActivityLoading(true)
                    }
                    is State.Success -> {
                        toogleActivityLoading(false)
                        it.data.body().notNull { productData->
                            if(productData[0].dc_BarcodeId.isNullOrEmpty() && productData[0].dc_BarcodeId.isNullOrEmpty()){
                                productError.value = barcodeID
                            }else{
                                product.value = Data(1, productData[0].dc_BarcodeId!!, productData[0].dc_ProductName!!,"")
                            }
                        }
                    }
                    is State.Fail -> {
                        toogleActivityLoading(false)
                    }
                    is State.Error -> {
                        toogleActivityLoading(false)
                        activityErrorOrFail(it.throwable)
                    }
                }
            }
        }
    }

}