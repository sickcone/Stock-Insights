package com.ubiq.stockinsights.domain.usecase

import android.util.Log
import androidx.compose.ui.res.booleanResource
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.ubiq.stockinsights.BuildConfig
import com.ubiq.stockinsights.data.model.MainWrapper
import com.ubiq.stockinsights.data.model.SymbolData
import com.ubiq.stockinsights.data.model.SymbolSearchResponse
import com.ubiq.stockinsights.data.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainUseCase @Inject constructor(private val repo: MainRepository) {

    suspend operator fun invoke(symbol: String): Flow<MainWrapper> = flow {
        repo.mainFetchData(
            symbol,
            hashMapOf<String, String>().apply {
                put("apikey", BuildConfig.API_KEY)
            }
        ).collect { apiResponse ->
            try {
                Log.d("TAGAG", apiResponse.toString())
                if (apiResponse == null || apiResponse.isEmpty) {
                    emit(MainWrapper.APIError("Data not found. Symbol maybe invalid"))
                    return@collect
                }
                val result: SymbolSearchResponse? = Gson().fromJson(apiResponse, SymbolSearchResponse::class.java)
                if (result == null) {
                    emit(MainWrapper.APIError("Data not found"))
                    return@collect
                }
                val items: List<SymbolData?> = result
                for (item: SymbolData? in items) {
                    if (item != null && item.symbol.equals(symbol, true)) {
                        emit(MainWrapper.MainDataSuccess(item))
                        break
                    }
                }
            } catch (e: Exception) {
                emit(MainWrapper.APIError(e.message ?: "Something went wrong"))
            }
        }
    }
}