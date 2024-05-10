package com.htrack.hnotes.ui.screen

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.htrack.hnotes.MainViewModel
import com.htrack.hnotes.R
import com.htrack.hnotes.ui.theme.AlertDialog
import com.htrack.hnotes.ui.theme.BackNavigationIcon
import com.htrack.hnotes.ui.theme.HHorizontalDivider
import com.htrack.hnotes.ui.theme.HTextField
import com.htrack.hnotes.ui.theme.ScreenCore

@Composable
fun CreateNoteScreen(
    navController: NavHostController,
    viewModel: MainViewModel,
) {
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
                    if (viewModel.selectedNote.value.link?.isNotEmpty() == true) {
                        viewModel.selectedNote.value.type = "link"
                    }
                    viewModel.addOrUpdateNote()
                    navController.popBackStack()
                })
        }) { pv ->
        val openUrlLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ -> }
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
                maxLines = 2,
                text = viewModel.selectedNote.value.title ?: "",
                hint = stringResource(R.string.enter_title_here),
                textStyle = MaterialTheme.typography.titleMedium
            ) { t ->
                viewModel.onTitleChanged(t)
            }
            HHorizontalDivider()
            HTextField(
                modifier = Modifier
                    .weight(1F)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(start = 8.dp, end = 8.dp),
                text = viewModel.selectedNote.value.info ?: "",
                hint = stringResource(R.string.enter_note_here),
                textStyle = MaterialTheme.typography.bodyMedium
            ) { t ->
                viewModel.onInfoChanged(t)
            }
            HHorizontalDivider()
            Row(
                modifier = Modifier.padding(end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                HTextField(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 8.dp, end = 8.dp),
                    text = viewModel.selectedNote.value.link ?: "",
                    hint = stringResource(R.string.enter_url_here),
                    maxLines = 3,
                    textStyle = MaterialTheme.typography.bodyMedium
                ) { t ->
                    viewModel.onLinkChanged(t)
                }
                Image(
                    modifier = Modifier.clickable {
                        if ((viewModel.selectedNote.value.link?.length ?: 0) > 0) {
                            val intent =
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(viewModel.selectedNote.value.link)
                                )
                            openUrlLauncher.launch(intent)
                        }
                    },
                    contentScale = ContentScale.FillHeight,
                    painter = painterResource(R.drawable.outline_open_in_new_24),
                    contentDescription = stringResource(R.string.content_open_url)
                )
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
    deleteClick: () -> Unit = {},
    shareClick: () -> Unit = {}
) {
    IconButton(onClick = { shareClick() }) {
        Icon(
            imageVector = Icons.Default.Share,
            contentDescription = stringResource(R.string.content_description_share)
        )
    }
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