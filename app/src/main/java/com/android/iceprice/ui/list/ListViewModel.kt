package com.android.iceprice.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.iceprice.SingleAction
import com.android.iceprice.UserLocalInfo
import com.android.iceprice.extensions.failure
import com.android.iceprice.extensions.loading
import com.android.iceprice.extensions.success
import com.android.iceprice.network.api.NetworkModule
import com.android.iceprice.network.model.getItem
import kotlinx.coroutines.launch
import com.android.iceprice.network.Result
import com.android.iceprice.ui.DetailItem
import com.android.iceprice.ui.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ListViewModel : ViewModel() {

    private val _state = MutableLiveData<ResultWrapper<List<DetailItem>, Throwable>>()
    val state: LiveData<ResultWrapper<List<DetailItem>, Throwable>> = _state

    private var items: List<DetailItem> = emptyList()

    private val _openDetail: MutableLiveData<SingleAction<DetailItem>> =
        MutableLiveData<SingleAction<DetailItem>>()
    val openDetail: LiveData<SingleAction<DetailItem>> = _openDetail

    private val _cityChanges = MutableLiveData<String>()
    val cityChanges: LiveData<String> = _cityChanges

    private var tab: ListFragment.Tab = ListFragment.Tab.SHOPS

    private var city = UserLocalInfo.citySlug
    private var cityName = UserLocalInfo.cityName

    fun loadCity(){
        cityName = UserLocalInfo.cityName
        _cityChanges.value = cityName
    }
    fun loadItems(tab: ListFragment.Tab) {
        this.tab = tab
        viewModelScope.launch {
            _state.loading()
            val result = try {
                requestItems(tab)
            } catch (e: Exception) {
                Result.Error(e)
            }

            when (result) {
                is Result.Success<List<DetailItem>> -> {
                    items = result.data
                    _state.success(result.data)
                }
                is Result.Error -> _state.failure(result.exception)
            }

        }
    }

    fun retry() {
        loadItems(tab)
    }

    fun changeCity() {
        city = UserLocalInfo.citySlug
        loadCity()
        loadItems(tab)
    }

    fun onClickPosition(position: Int) {
        _openDetail.postValue(SingleAction(items[position]))
    }

    private suspend fun requestItems(tab: ListFragment.Tab): Result.Success<List<DetailItem>> {
        return withContext(Dispatchers.IO) {
            Result.Success(
                when (tab) {
                    ListFragment.Tab.SHOPS -> NetworkModule.api.getShops(city).map { it.getItem() }
                    ListFragment.Tab.PROMOCODES -> NetworkModule.api.getPromocodes(city)
                        .map { it.getItem() }
                    ListFragment.Tab.GIFTS -> NetworkModule.api.getGifts(city).map { it.getItem() }
                    ListFragment.Tab.EVENTS -> NetworkModule.api.getEvents(city)
                        .map { it.getItem() }
                }
            )
        }
    }

}