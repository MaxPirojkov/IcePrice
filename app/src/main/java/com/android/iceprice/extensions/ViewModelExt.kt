package com.android.iceprice.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.android.iceprice.ui.ResultWrapper

inline fun <reified T : ViewModel> runViewModel(vm: T, body: T.() -> Unit): T {
    vm.body()
    return vm
}

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))


fun <T, E : Throwable> MutableLiveData<ResultWrapper<T, E>>.loading() {
    if (this != null) value = ResultWrapper.loading()
}

fun <T, E : Throwable> MutableLiveData<ResultWrapper<T, E>>.success(data: T) {
    if (this != null) value = ResultWrapper.success(data)
}

fun <T, E : Throwable> MutableLiveData<ResultWrapper<T, E>>.failure(error: E) {
    if (this != null) value = ResultWrapper.failure(error)
}