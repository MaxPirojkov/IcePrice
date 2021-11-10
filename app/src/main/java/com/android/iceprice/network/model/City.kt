package com.android.iceprice.network.model

import com.android.iceprice.UserLocalInfo
import com.google.gson.annotations.SerializedName

class City(
    @SerializedName("_id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("name_alt") val nameAlt: String,
    @SerializedName("code") val code: Int,
    @SerializedName("slug") val slug: String,
    @SerializedName("country") val country: Int,
) {
    fun getCityName() = if (UserLocalInfo.language == "en") nameAlt else name
}