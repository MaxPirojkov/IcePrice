package com.android.iceprice.giftfragment

import com.android.iceprice.giftfragment.model.EventItem
import retrofit2.http.GET

interface ApiFactoryEvent {

    @GET("api/moscow/events.json")
    suspend fun getEvents(): List<EventItem>

}