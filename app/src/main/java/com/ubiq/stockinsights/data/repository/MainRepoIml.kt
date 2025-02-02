package com.ubiq.stockinsights.data.repository

import com.google.gson.JsonArray
import com.ubiq.stockinsights.data.api.MainApiClient
import com.ubiq.stockinsights.util.RetrofitHelper
import com.ubiq.stockinsights.util.constants.MAIN_END_POINT
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepoIml @Inject constructor(private val client: MainApiClient) : MainRepository {

    override suspend fun mainFetchData(symbol: String, fieldMap: HashMap<String, String>): Flow<JsonArray?> {
        return RetrofitHelper.doApiCall {
            client.mainApi(
                url = MAIN_END_POINT + symbol,
                map = fieldMap
            )
        }
    }
}