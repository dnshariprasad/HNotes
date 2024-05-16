package com.htrack.htrack.ui.screen.create

import android.content.Intent
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.htrack.htrack.R
import com.htrack.htrack.data.models.Note
import com.htrack.htrack.isUrl
import com.htrack.htrack.shareTextIntent
import com.htrack.htrack.toActionViewIntent
import com.htrack.htrack.ui.screen.NoteTypes.NOTE_TYPE_LINK
import com.htrack.htrack.ui.theme.AlertDialog
import com.htrack.htrack.ui.theme.BackNavigationIcon
import com.htrack.htrack.ui.theme.HHorizontalDivider
import com.htrack.htrack.ui.theme.HTextField
import com.htrack.htrack.ui.theme.ScreenCore


@Composable
fun CreateNoteScreen(
    navController: NavHostController,
    viewModel: CreateNoteViewModel = viewModel()
) {

    var showAlert by remember { mutableStateOf(false) }
    val context = LocalContext.current

    ScreenCore(
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
                        viewModel.selectedNote.value.type = NOTE_TYPE_LINK
                    }
                    viewModel.addOrUpdateNote()
                    navController.popBackStack()
                },
                shareClick = {
                    val t = viewModel.shareNoteText()
                    if (t.isNotEmpty()) {
                        context.startActivity(
                            Intent.createChooser(
                                t.shareTextIntent(),
                                context.getString(R.string.share_note)
                            )
                        )
                    }
                })
        }) { pv ->
        viewModel.selectedNote.value =
            navController.previousBackStackEntry?.savedStateHandle?.get<Note>("note") ?: Note()
        val openUrlLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ -> }
        val customTextSelectionColors = TextSelectionColors(
            handleColor = MaterialTheme.colorScheme.onPrimary,
            backgroundColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.4f)
        )
        CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
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
                    textStyle = MaterialTheme.typography.titleMedium,
                    textStyleHint = MaterialTheme.typography.titleMedium
                ) { t ->
                    viewModel.onTitleChanged(t)
                }
                HHorizontalDivider()
                HTextField(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .weight(1F)
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(start = 8.dp, end = 8.dp),
                    text = viewModel.selectedNote.value.info ?: "",
                    hint = stringResource(R.string.enter_note_here),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    textStyleHint = MaterialTheme.typography.bodyMedium
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
                        textStyle = MaterialTheme.typography.bodyMedium,
                        textStyleHint = MaterialTheme.typography.bodyMedium
                    ) { t ->
                        viewModel.onLinkChanged(t)
                    }
                    Image(
                        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onPrimary),
                        modifier = Modifier.clickable {
                            if ((viewModel.selectedNote.value.link?.length ?: 0) == 0
                                || viewModel.selectedNote.value.link?.isUrl() == false
                            ) {
                                Toast.makeText(context, "Please enter title", Toast.LENGTH_SHORT)
                                    .show()
                                return@clickable
                            }
                            viewModel.selectedNote.value.link?.toActionViewIntent()?.let {
                                openUrlLauncher.launch(it)
                            }
                        },
                        painter = painterResource(R.drawable.outline_open_in_new_24),
                        contentDescription = stringResource(R.string.content_open_url)
                    )
                }

            }
        }
        if (showAlert) {
            AlertDialog(dialogText = stringResource(R.string.are_sure_do_you_want_to_delete)) {
                viewModel.deleteNote()
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
            painter = painterResource(R.drawable.outline_share_24),
            contentDescription = stringResource(R.string.content_description_share)
        )
    }
    if (showDelete) {
        IconButton(onClick = { deleteClick() }) {
            Icon(
                painter = painterResource(R.drawable.baseline_delete_outline_24),
                contentDescription = stringResource(R.string.content_description_delete)
            )
        }
    }
    IconButton(onClick = { addClick() }) {
        Icon(
            painter = painterResource(R.drawable.baseline_done_24),
            contentDescription = stringResource(R.string.content_description_done)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun MyComposablePreview() {
    CreateNoteScreen(
        navController = rememberNavController()
    )
}