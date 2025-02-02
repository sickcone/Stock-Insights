package com.ubiq.stockinsights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubiq.stockinsights.data.model.MainWrapper
import com.ubiq.stockinsights.data.model.ScreenUIState
import com.ubiq.stockinsights.domain.usecasehandler.MainUseCaseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainUseCaseHandler: MainUseCaseHandler) : ViewModel() {

    private var currentSymbol = ""

    private val _uiState = MutableStateFlow(ScreenUIState())
    val uiState: StateFlow<ScreenUIState> = _uiState.asStateFlow()

    fun handleSearch(symbol: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.emit(ScreenUIState(isLoading = true))
            currentSymbol = symbol
            mainUseCaseHandler.mainUseCase.invoke(symbol).collect {
                handleApiResponse(it)
            }
        }
    }

    fun handleRefresh() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.emit(ScreenUIState(isLoading = true))
            mainUseCaseHandler.mainUseCase.invoke(currentSymbol).collect {
                handleApiResponse(it)
            }
        }
    }

    private suspend fun handleApiResponse(mainWrapper: MainWrapper) {
        when (mainWrapper) {
            is MainWrapper.MainDataSuccess -> {
                if (mainWrapper.successResponse == null) {
                    return
                }
                _uiState.emit(ScreenUIState(mainWrapper.successResponse))
            }
            is MainWrapper.APIError -> {
                _uiState.emit(ScreenUIState(error = mainWrapper.message))
            }
        }
    }
}