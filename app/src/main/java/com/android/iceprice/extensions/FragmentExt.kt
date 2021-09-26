package com.android.iceprice.extensions

import androidx.fragment.app.Fragment
import com.android.iceprice.R

fun Fragment.replace(fragment: Fragment) {
    requireActivity().supportFragmentManager
        .beginTransaction()
        .replace(R.id.content, fragment)
        .addToBackStack(null)
        .commit()
}

fun Fragment.add(fragment: Fragment) {
    requireActivity().supportFragmentManager
        .beginTransaction()
        .add(R.id.content, fragment)
        .addToBackStack(null)
        .commit()
}