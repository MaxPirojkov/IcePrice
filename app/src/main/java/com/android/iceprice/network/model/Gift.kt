package com.android.iceprice.network.model

import com.android.iceprice.UserLocalInfo
import com.android.iceprice.ui.DetailItem
import com.android.iceprice.ui.Type
import com.google.gson.annotations.SerializedName

class Gift(
    @SerializedName("title_alt") val title_alt: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("shop") val shop: String? = null,
    @SerializedName("trusted_seller") val trustedSeller: Boolean? = null,
    @SerializedName("website") val website: String? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("body_alt") val body_alt: String? = null,
    @SerializedName("body") val body: String? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("address") val address: String? = null,
    @SerializedName("address_eng") val address_eng: String? = null
)

fun Gift.getItem() = if (UserLocalInfo.language == "en") {
    DetailItem(
        title = title_alt,
        shopName = shop,
        image = image,
        description = body_alt,
        address = address_eng,
        phone = phone,
        website = null,
        type = Type.GIFT
    )
} else {
    DetailItem(
        title = title,
        shopName = shop,
        image = image,
        description = body,
        address = address,
        phone = phone,
        website = null,
        type = Type.GIFT
    )
}