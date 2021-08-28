package com.afgdevlab.expirydate.ui.home.fragment

import androidx.databinding.ObservableField
import androidx.databinding.adapters.TextViewBindingAdapter
import androidx.hilt.lifecycle.ViewModelInject
import com.afgdevlab.expirydate.base.viewmodel.BaseViewModel
import com.afgdevlab.expirydate.data.preference.PreferenceHelperImp

/**
 * Created by hakanaksoy on 21.06.2021.
 * Loodos
 */

class AddCarcodeFragmentViewModel @ViewModelInject constructor(
    private val addCarcodeFragmentRepository: AddCarcodeFragmentRepository,
    var preferenceHelperImp: PreferenceHelperImp
) : BaseViewModel() {

    var barcode = ObservableField<String>()
    var name = ObservableField<String>()
    var date = ObservableField<String>()
    var lastDateControl = ObservableField<String>()


    var productBarcode = TextViewBindingAdapter.OnTextChanged { s, start, before, count ->
        barcode.set(s.toString())
    }

    var productName = TextViewBindingAdapter.OnTextChanged { s, start, before, count ->
        name.set(s.toString())
    }

    var productDate = TextViewBindingAdapter.OnTextChanged { s, start, before, count ->
        date.set(s.toString())
    }

}