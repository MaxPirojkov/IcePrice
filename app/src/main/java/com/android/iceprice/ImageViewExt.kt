package com.android.iceprice

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(imageUrl: String?) {
    GlideApp
        .with(this.context)
        .load(imageUrl)
//        .error(R.drawable.ic_discount)
        .into(this)

}