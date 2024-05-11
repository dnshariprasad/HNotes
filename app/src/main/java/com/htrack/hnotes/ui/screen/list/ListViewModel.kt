package com.htrack.hnotes.ui.screen.list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.htrack.hnotes.MyApp
import com.htrack.hnotes.data.Note

class ListViewModel : ViewModel() {
    val noteDao = MyApp.todoDatabase.getNotesDao()

    val noteList: LiveData<List<Note>> = noteDao.getAllNote()

    var selectedNote = mutableStateOf(Note())
}