package com.iyiyasa.android.ui.barcode


import com.iyiyasa.android.data.service.IyiyasaDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */


@ExperimentalCoroutinesApi
@Singleton
class BarcodeRepository @Inject constructor(
    private val iyiyasaDataSource: IyiyasaDataSource
) {

}