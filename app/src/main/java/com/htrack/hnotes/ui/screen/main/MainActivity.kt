package com.htrack.hnotes.ui.screen.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.htrack.hnotes.ui.screen.Screens

class MainActivity : ComponentActivity() {
    private lateinit var viewMode: MainViewModel
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewMode = ViewModelProvider(this)[MainViewModel::class.java]
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
