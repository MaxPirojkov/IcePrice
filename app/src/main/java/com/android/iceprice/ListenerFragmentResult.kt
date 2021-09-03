package com.android.iceprice

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner

typealias ResultListener<T> = (T) -> Unit

fun Fragment.fragmentListener(): ListenerFragmentResult {
    return requireActivity() as ListenerFragmentResult
}

interface ListenerFragmentResult {
    fun <T : Parcelable> listenResult(clazz: Class<T>, owner: LifecycleOwner, listener: ResultListener<T>)
}