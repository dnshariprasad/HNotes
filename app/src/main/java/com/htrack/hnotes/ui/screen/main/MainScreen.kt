package com.htrack.hnotes.ui.screen.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.htrack.hnotes.ui.screen.Screens.SCREEN_CREATE_NOTE
import com.htrack.hnotes.ui.screen.Screens.SCREEN_NOTE_LIST
import com.htrack.hnotes.ui.screen.create.CreateNoteScreen
import com.htrack.hnotes.ui.screen.list.NoteListScreen

@Composable
fun MainScreen(navController: NavHostController) {
    NavHost(navController, startDestination = SCREEN_NOTE_LIST) {
        composable(SCREEN_NOTE_LIST) {
            NoteListScreen(navController = navController)
        }
        composable(SCREEN_CREATE_NOTE) {
            CreateNoteScreen(navController = navController)
        }
    }
}