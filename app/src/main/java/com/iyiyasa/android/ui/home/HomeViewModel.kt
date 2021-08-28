package com.iyiyasa.android.ui.home

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import com.iyiyasa.android.base.viewmodel.BaseViewModel
import com.iyiyasa.android.data.persistence.AppDatabase
import com.iyiyasa.android.data.persistence.entity.Data
import com.iyiyasa.android.extensions.notNull
import com.iyiyasa.android.utils.SingleLiveData
import java.util.*


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

class HomeViewModel @ViewModelInject constructor(
    private val homeRepository: HomeRepository,
    private val appDatabase: AppDatabase
) : BaseViewModel() {

    var item = ObservableField(appDatabase.iyiyasaDAO().getData() as ArrayList<Data>)
    var product = SingleLiveData<Data>()
    var productDelete = SingleLiveData<Data>()

    fun add(data: Data?) {
        data.notNull {
            appDatabase.iyiyasaDAO().insertData(it)
            product.value = it
        }
    }

    fun delete(data:Data?){
        data.notNull {
            appDatabase.iyiyasaDAO().deleteData(it)
            productDelete.value = it
        }
    }
}