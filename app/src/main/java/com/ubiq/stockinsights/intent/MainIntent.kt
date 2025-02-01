package com.ubiq.stockinsights.intent


sealed class MainIntent {
    data class MainCall(val tabId: Int) : MainIntent()
}