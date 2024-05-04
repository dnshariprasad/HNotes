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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.htrack.hnotes.R
import com.htrack.hnotes.ui.screen.Screens.SCREEN_CREATE_NOTE
import com.htrack.hnotes.ui.theme.ScreenCore

@Composable
fun NoteListScreen(navController: NavHostController, notesList: List<String>) {
    ScreenCore(
        title = stringResource(R.string.your_notes),
        actions = { NoteListScreenActions(navController) }) { pv ->
        NoteList(
            navController = navController,
            paddingValues = pv,
            itemsList = notesList
        )
    }
}

@Composable
fun NoteListScreenActions(navController: NavHostController) {
    IconButton(onClick = { navController.navigate(SCREEN_CREATE_NOTE) }) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.content_description_add)
        )
    }
}

@Composable
fun NoteItem(navController: NavHostController, item: String) {
    Column {
        Text(
            modifier = Modifier
                .clickable { navController.navigate(SCREEN_CREATE_NOTE) }
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .padding(4.dp),
            text = item,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )
        HorizontalDivider(
            modifier = Modifier.padding(4.dp),
            color = MaterialTheme.colorScheme.onPrimary,
            thickness = 0.1.dp
        )
    }
}

@Composable
fun NoteList(
    navController: NavHostController,
    paddingValues: PaddingValues,
    itemsList: List<String>
) {
    if (itemsList.isEmpty())
        Text(
            text = stringResource(id = R.string.no_notes_found),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .wrapContentHeight(),
        )
    else
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            items(itemsList) { itemsListText ->
                NoteItem(navController, itemsListText)
            }
        }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ListScreenPreview() {
    val navController = rememberNavController()
    NoteListScreen(navController, mutableListOf())
}
