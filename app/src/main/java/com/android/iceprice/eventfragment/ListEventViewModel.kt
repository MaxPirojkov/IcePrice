package com.android.iceprice.giftfragment

import NetworkModuleEvent.eventsApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.iceprice.giftfragment.model.EventItem
import kotlinx.coroutines.launch

class ListEventViewModel : ViewModel() {

    private val _eventList = MutableLiveData<List<EventItem>>()
    val eventListMV: LiveData<List<EventItem>> = _eventList

    init {
        loadEvents()

    }


    private fun loadEvents() {
        viewModelScope.launch {
            val eventsResponse = eventsApi.getEvents()
            _eventList.value = eventsResponse
        }
    }
}