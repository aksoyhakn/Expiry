package com.iyiyasa.android.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import com.iyiyasa.android.base.viewmodel.BaseViewModel


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

class HomeViewModel @ViewModelInject constructor(
    private val homeRepository: HomeRepository
) : BaseViewModel() {
}