package com.htrack.htrack.ui.screen.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.htrack.htrack.ui.screen.Screens
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewMode : MainViewModel by viewModels()
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition { viewMode.isLoading.value }
        }
        setContent {
            navController = rememberNavController()
            MainScreen(navController)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.action?.let { action ->
            if (action == Intent.ACTION_SEND && intent.type == "text/plain") {
                navController.currentBackStackEntry?.savedStateHandle?.apply {
                    set("note", viewMode.handleShareData(intent.getStringExtra(Intent.EXTRA_TEXT)))
                }
                navController.navigate(Screens.SCREEN_CREATE_NOTE)
            }
        }
    }
}
