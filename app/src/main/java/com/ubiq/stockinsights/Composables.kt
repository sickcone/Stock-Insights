package com.ubiq.stockinsights

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ubiq.stockinsights.data.model.ClickInterface
import com.ubiq.stockinsights.data.model.ScreenUIState
import com.ubiq.stockinsights.ui.theme.Purple40
import kotlinx.coroutines.flow.StateFlow


@Composable
fun StockScreen(
    modifier: Modifier = Modifier,
    uiStateFlow: StateFlow<ScreenUIState>,
    callback: (ClickInterface) -> Unit
) {

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Column(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 250.dp)
                .align(Alignment.Center)
        ) {
            //Top Title and Search Bar for symbol searching
            SearchBar(callback)
            Spacer(modifier = Modifier.height(48.dp))
            //Main UI for displaying data and showing error message or for loaders
            MainUI(uiStateFlow, callback)
        }
    }
}

@Composable
fun SearchBar(callback: (ClickInterface) -> Unit) {
    var searchText by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Column {
        Spacer(modifier = Modifier.height(40.dp))

        //Title
        Text(
            "Check Stock",
            modifier = Modifier
                .fillMaxWidth(0.8f),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            color = Purple40
        )

        Spacer(modifier = Modifier.height(30.dp))

        //Text Field
        OutlinedTextField(
            value = searchText,
            onValueChange = { newText ->
                searchText = newText
            },
            modifier = Modifier
                .fillMaxWidth(0.8f),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    focusManager.clearFocus()
                    callback.invoke(ClickInterface.Search(searchText))
                }
            )
        )
    }
}

@Composable
fun MainUI(uiStateFlow: StateFlow<ScreenUIState>, callback: (ClickInterface) -> Unit) {

    val uiState by uiStateFlow.collectAsState()

    Box(modifier = Modifier.fillMaxWidth(0.8f), contentAlignment = Alignment.Center) {
        if (uiState.users != null) {
            val companyName = stringResource(R.string.company_name)
            val companySymbol = stringResource(R.string.company_symbol)
            val companyPrice = stringResource(R.string.price)
            val companyChange = stringResource(R.string.change)
            val volumeAverage = stringResource(R.string.volume_average)
            val marketCap = stringResource(R.string.market_cap)
            //Display main data and a refresh button
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LabelAndValue(companyName, uiState.users!!.companyName)
                LabelAndValue(companySymbol, uiState.users!!.symbol)
                LabelAndValue(companyPrice, uiState.users!!.price.toString())
                LabelAndValue(companyChange, uiState.users!!.changes.toString())
                LabelAndValue(volumeAverage, uiState.users!!.volAvg.toString())
                LabelAndValue(marketCap, uiState.users!!.mktCap.toString())
                Button(onClick = {
                    callback.invoke(ClickInterface.Refresh)
                }, colors = ButtonColors(Purple40, Color.White, Color.Gray, Color.White)) {
                    Text(text = "Refresh")
                }
            }
        } else if (uiState.error.isNullOrEmpty().not()) {
            //Error message
            Text(text = uiState.error!!)
        } else if (uiState.isLoading) {
            //Loader
            IndeterminateCircularIndicator()
        }
    }
}

@Composable
fun LabelAndValue(label: String, value: String) {
    Row {
        Text("$label : ")
        Text(value, color = Purple40)
    }
}

@Composable
fun IndeterminateCircularIndicator() {
    CircularProgressIndicator(
        modifier = Modifier.width(64.dp),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}