package com.android.iceprice.shopfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.android.iceprice.shopfragment.model.ShopItem
import com.android.iceprice.shopfragment.retrofit.NetworkModule.shopsApi
import kotlinx.coroutines.launch

class ListShopViewModel : ViewModel() {

    private val _shopList = MutableLiveData<List<ShopItem>>()
    val shopsListMV: LiveData<List<ShopItem>> = _shopList

    init {
        loadShops()
    }


    private fun loadShops() {
        viewModelScope.launch {
            val shopsResponse = shopsApi.getShops()
            _shopList.value = shopsResponse
        }
    }
}