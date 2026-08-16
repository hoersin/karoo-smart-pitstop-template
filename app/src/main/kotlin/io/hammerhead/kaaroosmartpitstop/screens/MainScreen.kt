package io.hammerhead.kaaroosmartpitstop.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import io.hammerhead.kaaroosmartpitstop.theme.AppTheme
import kotlinx.coroutines.delay

private val helloMessages = listOf(
    "Hello World",
    "Espresso",
    "Gravel",
    "Tailwind",
    "Coffee",
    "Bananas",
    "Ride On",
    "Keep Climbing",
    "Full Gas",
    "Nice Weather",
)

@Composable
fun MainScreen() {
    var messageIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L)
            messageIndex = (messageIndex + 1) % helloMessages.size
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = helloMessages[messageIndex],
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview(
    widthDp = 256,
    heightDp = 426,
)
@Composable
fun DefaultPreview() {
    AppTheme {
        MainScreen()
    }
}
