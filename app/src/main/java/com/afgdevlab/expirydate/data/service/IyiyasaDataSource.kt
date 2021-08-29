package com.afgdevlab.expirydate.data.service

import com.afgdevlab.expirydate.data.persistence.entity.Data
import com.afgdevlab.expirydate.ui.barcode.model.BarcodeResponse
import retrofit2.Response
import javax.inject.Inject


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

class IyiyasaDataSource @Inject constructor(
    private val iyiyasaService: IyiyasaService
) {
    suspend fun getBarcodeDataById(barcodeId: String): Response<ArrayList<BarcodeResponse>> =
        iyiyasaService.getBarcodeDataById("dc_BarcodeId=${barcodeId}")
}