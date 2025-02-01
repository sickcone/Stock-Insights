package com.ubiq.stockinsights.data.model


data class ScreenUIState(
    val users: SymbolData? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class ClickInterface {
    data class Search(val symbol: String) : ClickInterface()
    data object Refresh : ClickInterface()
}