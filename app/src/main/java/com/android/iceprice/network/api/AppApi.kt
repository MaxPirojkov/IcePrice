package com.android.iceprice.network.api

import com.android.iceprice.network.model.Event
import com.android.iceprice.network.model.Gift
import com.android.iceprice.network.model.Promocode
import com.android.iceprice.network.model.Shop
import retrofit2.http.GET
import retrofit2.http.Path

interface AppApi {

    @GET("api/{city}/sales.json")
    suspend fun getShops(@Path("city") city: String): List<Shop>

    @GET("api/{city}/promocodes.json")
    suspend fun getPromocodes(@Path("city") city: String): List<Promocode>

    @GET("api/{city}/gifts.json")
    suspend fun getGifts(@Path("city") city: String): List<Gift>

    @GET("api/{city}/events.json")
    suspend fun getEvents(@Path("city") city: String): List<Event>

}