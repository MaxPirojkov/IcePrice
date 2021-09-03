package com.android.iceprice.promocodefragment

import com.android.iceprice.giftfragment.model.EventItem
import com.android.iceprice.promocodefragment.model.PromocodeItem
import retrofit2.http.GET

interface ApiFactoryPromocodes {
    @GET("api/moscow/promocodes.json")
    suspend fun getPromocodes(): List<PromocodeItem>
}