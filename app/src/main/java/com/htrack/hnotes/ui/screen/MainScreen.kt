package com.htrack.hnotes.ui.screen

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.htrack.hnotes.MainViewModel
import com.htrack.hnotes.ui.screen.Screens.SCREEN_CREATE_NOTE
import com.htrack.hnotes.ui.screen.Screens.SCREEN_NOTE_LIST

@Composable
fun MainScreen(navController: NavHostController, mainViewModel: MainViewModel) {
    NavHost(navController, startDestination = SCREEN_NOTE_LIST) {
        composable(SCREEN_NOTE_LIST) {
            NoteListScreen(navController, mainViewModel)
        }
        composable(SCREEN_CREATE_NOTE) {
            CreateNoteScreen(
                navController = navController,
                viewModel = mainViewModel
            )
        }
    }
}