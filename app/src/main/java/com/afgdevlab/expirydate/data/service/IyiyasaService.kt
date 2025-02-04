package com.afgdevlab.expirydate.data.service

import com.afgdevlab.expirydate.data.persistence.entity.Data
import com.afgdevlab.expirydate.ui.barcode.model.BarcodeResponse
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
    ): Response<ArrayList<BarcodeResponse>>

}