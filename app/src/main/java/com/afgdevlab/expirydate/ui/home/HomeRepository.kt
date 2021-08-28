package com.afgdevlab.expirydate.ui.home


import com.afgdevlab.expirydate.data.service.IyiyasaDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */


@ExperimentalCoroutinesApi
@Singleton
class HomeRepository @Inject constructor(
    private val iyiyasaDataSource: IyiyasaDataSource
) {

}