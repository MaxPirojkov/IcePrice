package com.android.iceprice.promocodefragment

import com.android.iceprice.shopfragment.model.ShopItem
import retrofit2.http.GET

interface ApiFactoryPromocode {

    @GET("api/moscow/promocodes.json")
    suspend fun getPromocodes(): List<ShopItem>

}