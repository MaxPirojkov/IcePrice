package com.android.iceprice.shopfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.android.iceprice.shopfragment.model.ShopItem
import com.android.iceprice.shopfragment.retrofit.NetworkModule.shopsApi
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.launch

class ListShopViewModel : ViewModel() {
    private val cityChoice: Disposable? = null

    private val _shopList = MutableLiveData<List<ShopItem>>()
    val shopsListMV: LiveData<List<ShopItem>> = _shopList

    private val _progressChanges = MutableLiveData<Boolean>()
    var progressChanges: LiveData<Boolean> = _progressChanges

    init {
        loadShops()
    }

//    private fun loadShops() {
//        viewModelScope.launch {
//            val shopsResponse = shopsApi.getShops()
//            _shopList.value = shopsResponse
//
//        }
//    }




    private fun loadShops() {
        viewModelScope.launch {
            val shopsResponse = shopsApi.getShops()
            if (shopsResponse.isNotEmpty()) {
                _shopList.value = shopsResponse
                _progressChanges.value = false
            } else {
                _progressChanges.value = true
            }
        }
    }
}
