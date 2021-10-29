package com.android.iceprice.network.model

import com.android.iceprice.ui.DetailItem
import com.android.iceprice.ui.DetailItemEng
import com.android.iceprice.ui.Type
import com.google.gson.annotations.SerializedName

class Shop(
    @SerializedName("title_alt") val titleEng: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("shop") val shop: String? = null,
    @SerializedName("trusted_seller") val trustedSeller: Boolean? = null,
    @SerializedName("website") val website: String? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("body_alt") val bodyEng: String? = null,
    @SerializedName("body") val body: String? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("address") val address: String? = null,
    @SerializedName("address_eng") val addressEng: String? = null
)

fun Shop.getItem() = DetailItem(
    title = title,
    shopName = shop,
    image = image,
    description = body,
    address = address,
    phone = phone,
    website = null,
    type = Type.SHOP
)

fun Shop.getItemEng() = DetailItemEng(
    titleEng = titleEng,
    shopName = shop,
    image = image,
    descriptionEng = bodyEng,
    addressEng = addressEng,
    phone = phone,
    website = null,
    type = Type.SHOP
)
