package com.htrack.hnotes.ui.screen

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.htrack.hnotes.MainViewModel
import com.htrack.hnotes.R
import com.htrack.hnotes.ui.theme.BackNavigationIcon
import com.htrack.hnotes.ui.theme.ScreenCore

@Composable
fun CreateNoteScreen(navController: NavHostController, viewModel: MainViewModel) {
    var note by remember { mutableStateOf("") }
    ScreenCore(
        title = stringResource(R.string.add_note),
        navigationIcon = {
            BackNavigationIcon {
                navController.popBackStack()
            }
        },
        actions = {
            CreateNoteScreenActions(navController, viewModel, note)
        }) { pv ->
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(pv)
                .padding(start = 8.dp, end = 8.dp),
            value = note,
            onValueChange = { t ->
                note = t
            },
            placeholder = { Text(stringResource(R.string.enter_here)) },
            colors = TextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onPrimary,
                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            )
        )
    }
}

@Composable
fun CreateNoteScreenActions(
    navController: NavHostController,
    viewModel: MainViewModel,
    note: String
) {
    IconButton(onClick = {
        if (note.trim().isEmpty()) {
            return@IconButton
        }
        viewModel.addNote(note)
        navController.popBackStack()
    }) {
        Icon(
            imageVector = Icons.Default.Done,
            contentDescription = stringResource(R.string.content_description_done)
        )
    }
}