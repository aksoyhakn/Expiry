package com.afgdevlab.expirydate.ui.barcode


import com.afgdevlab.expirydate.data.service.IyiyasaDataSource
import com.afgdevlab.expirydate.data.service.util.NetworkBoundRepository
import com.afgdevlab.expirydate.data.service.util.State
import com.afgdevlab.expirydate.ui.barcode.model.BarcodeResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
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

    fun getBarcodeDataById(barcodeId: String): Flow<State<BarcodeResponse>> {
        return object : NetworkBoundRepository<BarcodeResponse, BarcodeResponse>() {
            override suspend fun fetchFromRemote(): Response<BarcodeResponse> =
                iyiyasaDataSource.getBarcodeDataById(barcodeId)
        }.asFlow()
    }


}