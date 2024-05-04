package com.htrack.hnotes.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.htrack.hnotes.MainViewModel
import com.htrack.hnotes.R
import com.htrack.hnotes.data.Note
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
        viewModel.selectedNote = null
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
    if (itemsList.isEmpty()) Text(
        text = stringResource(id = R.string.no_notes_found),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .wrapContentHeight(),
    )
    else LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
            .padding(start = 16.dp, end = 16.dp)
    ) {
        items(itemsList) { n ->
            NoteItem(n) {
                viewModel.selectedNote = n
                navController.navigate(SCREEN_CREATE_NOTE)
            }
        }
    }
}

@Composable
fun NoteItem(item: Note, clickable: () -> Unit) {
    Column {
        Text(modifier = Modifier
            .clickable { clickable() }
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .padding(4.dp),
            text = item.info ?: "",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onPrimary)
        HorizontalDivider(
            modifier = Modifier.padding(4.dp),
            color = MaterialTheme.colorScheme.onPrimary,
            thickness = 0.1.dp
        )
    }
}