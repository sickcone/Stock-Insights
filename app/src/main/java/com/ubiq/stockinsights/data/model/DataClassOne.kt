package com.ubiq.stockinsights.data.model

import androidx.annotation.Keep

@Keep
class SymbolSearchResponse : ArrayList<SymbolData>()

@Keep
data class SymbolData(
    val address: String,
    val beta: Double,
    val ceo: String,
    val changes: Double,
    val cik: String,
    val city: String,
    val companyName: String,
    val country: String,
    val currency: String,
    val cusip: String,
    val dcf: Double,
    val dcfDiff: Double,
    val defaultImage: Boolean,
    val description: String,
    val exchange: String,
    val exchangeShortName: String,
    val fullTimeEmployees: String,
    val image: String,
    val industry: String,
    val ipoDate: String,
    val isActivelyTrading: Boolean,
    val isAdr: Boolean,
    val isEtf: Boolean,
    val isFund: Boolean,
    val isin: String,
    val lastDiv: Double,
    val mktCap: Long,
    val phone: String,
    val price: Double,
    val range: String,
    val sector: String,
    val state: String,
    val symbol: String,
    val volAvg: Int,
    val website: String,
    val zip: String
)