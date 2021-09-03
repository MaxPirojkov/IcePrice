package com.android.iceprice.giftfragment

import com.android.iceprice.giftfragment.model.GiftItem
import retrofit2.http.GET

interface ApiFactoryGift {

    @GET("api/moscow/gifts.json")
    suspend fun getGifts(): List<GiftItem>

}