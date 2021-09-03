package com.android.iceprice.giftfragment

import NetworkModuleGift.giftsApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.iceprice.giftfragment.model.GiftItem
import kotlinx.coroutines.launch

class ListGiftViewModel : ViewModel() {

    private val _giftList = MutableLiveData<List<GiftItem>>()
    val giftListMV: LiveData<List<GiftItem>> = _giftList

    init {
        loadGifts()
    }


    private fun loadGifts() {
        viewModelScope.launch {
            val giftsResponse = giftsApi.getGifts()
            _giftList.value = giftsResponse
        }
    }
}