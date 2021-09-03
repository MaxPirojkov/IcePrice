package com.android.iceprice.promocodefragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.iceprice.promocodefragment.model.PromocodeItem
import com.android.iceprice.promocodefragment.retrofitpromocode.NetworkModulePromocode
import kotlinx.coroutines.launch

class ListPromocodesViewModel: ViewModel() {
    private val _promocodeList = MutableLiveData<List<PromocodeItem>>()
    val promocodeListMV: LiveData<List<PromocodeItem>> = _promocodeList

    init {
        loadEvents()
    }


    private fun loadEvents() {
        viewModelScope.launch {
            val promocodesResponse = NetworkModulePromocode.promoApi.getPromocodes()
            _promocodeList.value = promocodesResponse
        }
    }

}