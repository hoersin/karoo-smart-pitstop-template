package io.hammerhead.kaaroosmartpitstop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.hammerhead.kaaroosmartpitstop.screens.MainScreen
import io.hammerhead.kaaroosmartpitstop.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                MainScreen()
            }
        }
    }
}
