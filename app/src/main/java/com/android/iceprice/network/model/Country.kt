package com.android.iceprice.network.model

import com.google.gson.annotations.SerializedName

class Country(
    @SerializedName("_id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("name_alt") val nameAlt: String,
    @SerializedName("code") val code: Int
)