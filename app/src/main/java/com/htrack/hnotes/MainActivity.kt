package com.htrack.hnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.htrack.hnotes.ui.screen.MainScreen

class MainActivity : ComponentActivity() {
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setContent {
            MainScreen(mainViewModel)
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MainScreenPreview() {
    lateinit var mainViewModel: MainViewModel
    MainScreen(mainViewModel)
}


