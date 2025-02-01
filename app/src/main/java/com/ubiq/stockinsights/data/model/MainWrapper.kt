package com.ubiq.stockinsights.data.model

sealed class MainWrapper {
    data class MainDataSuccess(val successResponse: SymbolData? = null) : MainWrapper()
    data class APIError(val message: String) : MainWrapper()
}
