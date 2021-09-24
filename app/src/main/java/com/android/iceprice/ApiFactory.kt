package com.android.iceprice

import com.android.iceprice.giftfragment.model.EventItem
import com.android.iceprice.giftfragment.model.GiftItem
import com.android.iceprice.promocodefragment.model.PromocodeItem
import com.android.iceprice.shopfragment.model.ShopItem
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiFactory {

//    @GET("api/{city}/sales.json")
//    suspend fun getShops(@Path("city") city: String): List<ShopItem>
//
//    @GET("api/{city}/promocodes.json")
//    suspend fun getPromocodes(@Path("city") city: String): List<PromocodeItem>
//
//    @GET("api/{city}/gifts.json")
//    suspend fun getGifts(@Path("city") city: String): List<GiftItem>
//
//    @GET("api/{city}/events.json")
//    suspend fun getEvents(@Path("city") city: String): List<EventItem>

    @GET("api/moscow/sales.json")
    suspend fun getShops(): List<ShopItem>

    @GET("api/moscow/promocodes.json")
    suspend fun getPromocodes(): List<PromocodeItem>

    @GET("api/moscow/gifts.json")
    suspend fun getGifts(): List<GiftItem>

    @GET("api/moscow/events.json")
    suspend fun getEvents(): List<EventItem>

}