package com.afgdevlab.expirydate.ui.splash

import androidx.hilt.lifecycle.ViewModelInject
import com.afgdevlab.expirydate.base.viewmodel.BaseViewModel


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

class SplashViewModel @ViewModelInject constructor(
    private val splashRepository: SplashRepository
) : BaseViewModel() {
}