package com.htrack.hnotes.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.htrack.hnotes.MainViewModel
import com.htrack.hnotes.R
import com.htrack.hnotes.data.Note
import com.htrack.hnotes.ui.screen.NoteTypes.NOTE_TYPE_LINK
import com.htrack.hnotes.ui.screen.NoteTypes.NOTE_TYPE_LOCATION
import com.htrack.hnotes.ui.screen.Screens.SCREEN_CREATE_NOTE
import com.htrack.hnotes.ui.theme.ScreenCore

@Composable
fun NoteListScreen(navController: NavHostController, viewModel: MainViewModel) {
    val notesList by viewModel.noteList.observeAsState()

    ScreenCore(title = stringResource(R.string.your_notes),
        actions = { NoteListScreenActions(navController, viewModel) }) { pv ->
        NoteList(
            navController = navController,
            viewModel = viewModel,
            paddingValues = pv,
            itemsList = notesList ?: emptyList()
        )
    }
}

@Composable
fun NoteListScreenActions(navController: NavHostController, viewModel: MainViewModel) {
    IconButton(onClick = {
        viewModel.selectedNote = mutableStateOf(Note())
        navController.navigate(SCREEN_CREATE_NOTE)
    }) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.content_description_add)
        )
    }
}

@Composable
fun NoteList(
    navController: NavHostController,
    viewModel: MainViewModel,
    paddingValues: PaddingValues,
    itemsList: List<Note>
) {
    val openUrlLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ -> }
    if (itemsList.isEmpty()) Text(
        text = stringResource(id = R.string.no_notes_found),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .wrapContentHeight(),
        fontFamily = FontFamily(Font(R.font.poppins_regular))
    )
    else LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
            .padding(start = 16.dp, end = 16.dp)
    ) {
        items(itemsList) { n ->
            NoteItem(n,
                locationClickable = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(n.link))
                    openUrlLauncher.launch(intent)
                },
                linkClickable = {

                }) {
                viewModel.selectedNote = mutableStateOf(n)
                navController.navigate(SCREEN_CREATE_NOTE)
            }
        }
    }
}

@Composable
fun NoteItem(
    item: Note,
    linkClickable: () -> Unit,
    locationClickable: () -> Unit,
    clickable: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(2.dp)
            .clickable { clickable() },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ),
        border = BorderStroke(
            0.1.dp,
            Color.Gray
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                if (true != item.title?.isEmpty())
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = item.title ?: "",
                        fontSize = 14.sp,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontFamily = FontFamily(Font(R.font.poppins_semibold))
                    )
                if (true != item.info?.isEmpty())
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = item.info ?: "",
                        fontSize = 14.sp,
                        maxLines = 2,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontFamily = FontFamily(Font(R.font.poppins_regular))
                    )
            }
            Image(
                modifier = Modifier.clickable {
                    when (item.type) {
                        NOTE_TYPE_LINK -> {
                            locationClickable()
                        }

                        NOTE_TYPE_LOCATION -> {
                            linkClickable()
                        }

                        else -> {}
                    }

                },
                contentScale = ContentScale.FillHeight, painter = painterResource(
                    when (item.type) {
                        NOTE_TYPE_LINK -> {
                            R.drawable.outline_open_in_new_24
                        }

                        NOTE_TYPE_LOCATION -> {
                            R.drawable.outline_open_in_new_24
                        }

                        else -> {
                            R.drawable.outline_notes_24
                        }
                    }
                ), contentDescription = stringResource(R.string.content_open_url)
            )
        }
    }

}