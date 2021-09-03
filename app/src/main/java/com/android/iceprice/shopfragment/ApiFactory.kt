package com.android.iceprice.shopfragment

import com.android.iceprice.shopfragment.model.ShopItem
import retrofit2.http.GET

interface ApiFactory {

    @GET("api/moscow/sales.json")
    suspend fun getShops(): List<ShopItem>

}