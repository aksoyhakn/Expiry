package com.iyiyasa.android.ui.home

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import com.iyiyasa.android.base.viewmodel.BaseViewModel
import com.iyiyasa.android.data.persistence.AppDatabase
import com.iyiyasa.android.data.persistence.entity.Data
import com.iyiyasa.android.ui.barcode.model.BarcodeResponse
import java.util.*
import kotlin.random.Random


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

class HomeViewModel @ViewModelInject constructor(
    private val homeRepository: HomeRepository,
    private val appDatabase: AppDatabase
) : BaseViewModel() {

    var item = ObservableField<ArrayList<Data>>()

    fun add(){
        appDatabase.iyiyasaDAO().insertData(Data(null, Random.nextInt().toString(),"5449000011114","COCA COLA 200ML"))
        item.set(appDatabase.iyiyasaDAO().getData() as ArrayList<Data>)
    }

}