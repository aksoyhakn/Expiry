package com.afgdevlab.expirydate.ui.home

import androidx.databinding.ObservableField
import androidx.databinding.adapters.TextViewBindingAdapter
import androidx.hilt.lifecycle.ViewModelInject
import com.afgdevlab.expirydate.base.viewmodel.BaseViewModel
import com.afgdevlab.expirydate.data.persistence.AppDatabase
import com.afgdevlab.expirydate.data.persistence.entity.Data
import com.afgdevlab.expirydate.data.persistence.entity.NotificationChannelID
import com.afgdevlab.expirydate.extensions.dayOfMonth
import com.afgdevlab.expirydate.extensions.monthOfName
import com.afgdevlab.expirydate.extensions.notNull
import com.afgdevlab.expirydate.extensions.year
import com.afgdevlab.expirydate.utils.SingleLiveData
import java.util.*


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

class HomeViewModel @ViewModelInject constructor(
    private val homeRepository: HomeRepository,
    val appDatabase: AppDatabase
) : BaseViewModel() {

    var item = ObservableField(appDatabase.iyiyasaDAO().getData() as ArrayList<Data>)
    var product = SingleLiveData<Data>()
    var productDelete = SingleLiveData<Data>()

    var searchKey = ObservableField<String>()
    var search = TextViewBindingAdapter.OnTextChanged { s, start, before, count ->
        searchKey.set(s.toString())
    }

    fun add(data: Data?) {
        data.notNull {
            appDatabase.iyiyasaDAO().insertData(it)
            product.value = it
        }
    }

    fun delete(data:Data?){
        data.notNull {
            appDatabase.iyiyasaDAO().deleteData(it)
            deleteNotificationChannel(it.productName)
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


    private fun deleteNotificationChannel(name :String){
        name.notNull {
            var notificationChannelID = appDatabase.iyiyasaDAO().getNotificationChannelID(name)
            appDatabase.iyiyasaDAO().deleteNotificationChannelID(notificationChannelID)
        }
    }
}