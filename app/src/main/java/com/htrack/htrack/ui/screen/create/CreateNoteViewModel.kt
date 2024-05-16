package com.htrack.htrack.ui.screen.create

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.htrack.htrack.MyApp
import com.htrack.htrack.data.models.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateNoteViewModel : ViewModel() {
    val noteDao = MyApp.todoDatabase.getNotesDao()

    var selectedNote = mutableStateOf(Note())

    fun onTitleChanged(newTitle: String?) {
        selectedNote.value = selectedNote.value.copy(title = newTitle)
    }

    fun onInfoChanged(newInfo: String?) {
        selectedNote.value = selectedNote.value.copy(info = newInfo)
    }

    fun onLinkChanged(link: String?) {
        selectedNote.value = selectedNote.value.copy(link = link)
    }

    fun shareNoteText(): String {
        val text = StringBuilder()
        if (true == selectedNote.value.title?.isNotEmpty()) {
            if (text.isNotEmpty())
                text.append("\n")
            text.append(selectedNote.value.title)
        }
        if (true == selectedNote.value.info?.isNotEmpty()) {
            if (text.isNotEmpty())
                text.append("\n")
            text.append(selectedNote.value.info)
        }
        if (true == selectedNote.value.link?.isNotEmpty()) {
            if (text.isNotEmpty())
                text.append("\n")
            text.append(selectedNote.value.link)
        }
        return text.toString()
    }

    fun addOrUpdateNote() {
        if (0 != selectedNote.value.id) {
            updateNote()
        } else {
            addNote()
        }
    }

    private fun addNote() {
        selectedNote.value.let {
            viewModelScope.launch(Dispatchers.IO) {
                noteDao.addNote(it)
            }
        }
    }

    private fun updateNote() {
        selectedNote.value.let {
            viewModelScope.launch(Dispatchers.IO) {
                noteDao.updateNote(it)
            }
        }
    }

    fun deleteNote() {
        selectedNote.value.id.let {
            viewModelScope.launch(Dispatchers.IO) {
                noteDao.deleteNote(it)
            }
        }
    }
}