package com.android.iceprice.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.iceprice.R

fun AppCompatActivity.openFragment(fragment: Fragment) {
    supportFragmentManager
        .beginTransaction()
        .replace(R.id.content, fragment)
        .commit()
}

