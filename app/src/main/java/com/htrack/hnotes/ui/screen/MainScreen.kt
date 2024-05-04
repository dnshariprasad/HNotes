package com.htrack.hnotes.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.htrack.hnotes.data.Note
import com.htrack.hnotes.ui.screen.Screens.SCREEN_CREATE_NOTE
import com.htrack.hnotes.ui.screen.Screens.SCREEN_NOTE_LIST

@Composable
fun MainScreen() {
    val notesList = mutableListOf<Note>()
    val navController = rememberNavController()
    NavHost(navController, startDestination = SCREEN_NOTE_LIST) {
        composable(SCREEN_NOTE_LIST) {
            NoteListScreen(navController, notesList)
        }
        composable(SCREEN_CREATE_NOTE) {
            CreateNoteScreen(navController, notesList)
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}
