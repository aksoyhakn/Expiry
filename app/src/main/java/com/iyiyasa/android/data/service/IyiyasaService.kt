package com.iyiyasa.android.data.service

import com.iyiyasa.android.ui.barcode.model.BarcodeResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query


/**
 * Created by hakanaksoy on 11.08.2021.
 * Loodos
 */

interface IyiyasaService {

    @POST("5d127d27")
    suspend fun getBarcodeDataById(
        @Query("\$where") barcodeId: String
    ): Response<BarcodeResponse>

}