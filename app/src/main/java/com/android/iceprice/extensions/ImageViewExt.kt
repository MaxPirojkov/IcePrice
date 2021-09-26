package com.android.iceprice.extensions

import android.widget.ImageView
import com.android.iceprice.GlideApp

fun ImageView.loadImage(imageUrl: String?) {
    GlideApp
        .with(this.context)
        .load(imageUrl)
//        .error(R.drawable.ic_discount)
        .into(this)

}