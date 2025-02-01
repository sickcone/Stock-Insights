package com.ubiq.stockinsights.data.api

import com.google.gson.JsonArray
import com.ubiq.stockinsights.util.constants.MAIN_END_POINT
import org.json.JSONArray
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface MainApiClient {

    @GET
    suspend fun mainApi(
        @Url url: String = MAIN_END_POINT,
        @QueryMap map: HashMap<String, String>
    ): JsonArray?

}