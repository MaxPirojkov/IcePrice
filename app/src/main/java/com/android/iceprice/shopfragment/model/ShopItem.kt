package com.android.iceprice.shopfragment.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShopItem(
    @SerialName("title_alt") val title_alt: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("shop") val shop: String? = null,
    @SerialName("trusted_seller") val trustedSeller: Boolean? = null,
    @SerialName("website") val website: String? = null,
    @SerialName("image") val image: String? = null,
    @SerialName("body_alt") val body_alt: String? = null,
    @SerialName("body") val body: String? = null,
    @SerialName("phone") val phone: String? = null,
    @SerialName("address") val address: String? = null,
    @SerialName("address_eng") val addressEn: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title_alt)
        parcel.writeString(title)
        parcel.writeString(shop)
        parcel.writeValue(trustedSeller)
        parcel.writeString(website)
        parcel.writeString(image)
        parcel.writeString(body_alt)
        parcel.writeString(body)
        parcel.writeString(phone)
        parcel.writeString(address)
        parcel.writeString(addressEn)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ShopItem> {
        override fun createFromParcel(parcel: Parcel): ShopItem {
            return ShopItem(parcel)
        }

        override fun newArray(size: Int): Array<ShopItem?> {
            return arrayOfNulls(size)
        }
    }
}