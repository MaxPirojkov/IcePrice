package com.android.iceprice.network.model

import com.android.iceprice.UserLocalInfo
import com.google.gson.annotations.SerializedName

class Country(
    @SerializedName("_id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("name_alt") val nameAlt: String,
    @SerializedName("code") val code: Int
){
    fun getCountryName() = if (UserLocalInfo.language == "en") nameAlt else name
}