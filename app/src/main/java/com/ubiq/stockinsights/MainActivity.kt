package com.ubiq.stockinsights

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ubiq.stockinsights.data.model.ClickInterface
import com.ubiq.stockinsights.ui.theme.PurpleGrey40
import com.ubiq.stockinsights.ui.theme.PurpleGrey80
import com.ubiq.stockinsights.ui.theme.StockInsightsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StockInsightsTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), containerColor = PurpleGrey80) { innerPadding ->
                    StockScreen(modifier = Modifier.padding(innerPadding), viewModel.uiState) {
                        when(it) {
                            ClickInterface.Refresh -> {
                                viewModel.handleRefresh()
                            }
                            is ClickInterface.Search -> {
                                viewModel.handleSearch(it.symbol)
                            }
                        }
                    }
                }
            }
        }
    }
}
