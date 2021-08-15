package com.iyiyasa.android.ui.home.fragment

import androidx.hilt.lifecycle.ViewModelInject
import com.iyiyasa.android.base.viewmodel.BaseViewModel
import com.iyiyasa.android.data.preference.PreferenceHelperImp

/**
 * Created by hakanaksoy on 21.06.2021.
 * Loodos
 */

class AddCarcodeFragmentViewModel @ViewModelInject constructor(
    private val addCarcodeFragmentRepository: AddCarcodeFragmentRepository,
    var preferenceHelperImp: PreferenceHelperImp
) : BaseViewModel() {

}