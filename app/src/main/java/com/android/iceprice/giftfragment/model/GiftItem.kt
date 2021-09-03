package com.android.iceprice.giftfragment.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GiftItem(
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
    @SerialName("address_eng") val address_eng: String? = null
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
        parcel.writeString(address_eng)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GiftItem> {
        override fun createFromParcel(parcel: Parcel): GiftItem {
            return GiftItem(parcel)
        }

        override fun newArray(size: Int): Array<GiftItem?> {
            return arrayOfNulls(size)
        }
    }
}