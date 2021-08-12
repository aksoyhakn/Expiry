package com.iyiyasa.android.data.service

import com.iyiyasa.android.ui.barcode.model.BarcodeResponse
import retrofit2.Response
import javax.inject.Inject


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

class IyiyasaDataSource @Inject constructor(
    private val iyiyasaService: IyiyasaService
) {
    suspend fun getBarcodeDataById(barcodeId: String): Response<BarcodeResponse> =
        iyiyasaService.getBarcodeDataById("dc_BarcodeId=${barcodeId}")
}