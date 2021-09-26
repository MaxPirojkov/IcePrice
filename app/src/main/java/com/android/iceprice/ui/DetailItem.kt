package com.android.iceprice.ui

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DetailItem(
    val title: String?,
    val shopName: String?,
    val description: String?,
    val image: String?,
    val address: String?,
    val phone: String?,
    val website: String?,
    val type: Type
) : Parcelable


enum class Type {
    EVENT,
    GIFT,
    PROMOCODE,
    SHOP
}