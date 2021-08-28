package com.iyiyasa.android.ui.home

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import com.iyiyasa.android.base.viewmodel.BaseViewModel
import com.iyiyasa.android.data.persistence.AppDatabase
import com.iyiyasa.android.data.persistence.entity.Data
import com.iyiyasa.android.extensions.dayOfMonth
import com.iyiyasa.android.extensions.monthOfName
import com.iyiyasa.android.extensions.notNull
import com.iyiyasa.android.extensions.year
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

    fun update(data:Data?){
        data.notNull {
            data?.isOpenProduct=true
            data?.isOpenProductDate =  "${
                String.format(
                    "%02d",
                    System.currentTimeMillis().dayOfMonth
                )
            } ${System.currentTimeMillis().monthOfName} ${System.currentTimeMillis().year}"
            appDatabase.iyiyasaDAO().updateData(it)
        }
    }
}