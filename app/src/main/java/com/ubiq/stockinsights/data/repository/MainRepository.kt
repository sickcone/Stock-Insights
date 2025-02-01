package com.ubiq.stockinsights.data.repository

import com.google.gson.JsonArray
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun mainFetchData(symbol: String, fieldMap: HashMap<String, String>): Flow<JsonArray?>
}