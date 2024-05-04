package com.htrack.hnotes.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.htrack.hnotes.MainViewModel
import com.htrack.hnotes.R
import com.htrack.hnotes.ui.theme.AlertDialog
import com.htrack.hnotes.ui.theme.BackNavigationIcon
import com.htrack.hnotes.ui.theme.HTextField
import com.htrack.hnotes.ui.theme.ScreenCore

@Composable
fun CreateNoteScreen(navController: NavHostController, viewModel: MainViewModel) {
    var showAlert by remember { mutableStateOf(false) }
    val context = LocalContext.current // Moved LocalContext inside NoteList

    ScreenCore(
        title = stringResource(R.string.add_note),
        navigationIcon = {
            BackNavigationIcon {
                navController.popBackStack()
            }
        },
        actions = {
            CreateNoteScreenActions(
                0 != viewModel.selectedNote.value.id,
                deleteClick = {
                    showAlert = !showAlert
                },
                addClick = {
                    if (viewModel.selectedNote.value.title?.isEmpty() == true) {
                        Toast.makeText(context, "Please enter title", Toast.LENGTH_SHORT).show()
                        return@CreateNoteScreenActions
                    }
                    if (viewModel.selectedNote.value.info?.isEmpty() == true) {
                        Toast.makeText(context, "Please enter info", Toast.LENGTH_SHORT).show()
                        return@CreateNoteScreenActions
                    }
                    viewModel.addOrUpdateNote()
                    navController.popBackStack()
                })
        }) { pv ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pv)
        ) {
            HTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = 8.dp, end = 8.dp),
                text = viewModel.selectedNote.value.title ?: "",
                hint = stringResource(R.string.enter_title_here)
            ) { t ->
                viewModel.onTitleChanged(t)
            }
            HTextField(
                modifier = Modifier
                    .weight(1F)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(start = 8.dp, end = 8.dp),
                text = viewModel.selectedNote.value.info ?: "",
                hint = stringResource(R.string.enter_note_here)
            ) { t ->
                viewModel.onInfoChanged(t)
            }
        }
        if (showAlert) {
            AlertDialog(dialogText = stringResource(R.string.are_sure_do_you_want_to_delete)) {
                viewModel.deleteTodo()
                navController.popBackStack()
                showAlert = !showAlert
            }
        }
    }
}

@Composable
fun CreateNoteScreenActions(
    showDelete: Boolean = false,
    addClick: () -> Unit = {},
    deleteClick: () -> Unit = {}
) {
    if (showDelete) {
        IconButton(onClick = { deleteClick() }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(R.string.content_description_delete)
            )
        }
    }
    IconButton(onClick = { addClick() }) {
        Icon(
            imageVector = Icons.Default.Done,
            contentDescription = stringResource(R.string.content_description_done)
        )
    }
}