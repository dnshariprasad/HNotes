package com.htrack.hnotes

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.htrack.hnotes.data.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val noteDao = MyApp.todoDatabase.getNotesDao()

    val noteList: LiveData<List<Note>> = noteDao.getAllNote()

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

    fun deleteTodo() {
        selectedNote.value.id.let {
            viewModelScope.launch(Dispatchers.IO) {
                noteDao.deleteNote(it)
            }
        }
    }

    fun handleShareData(stringExtra: String?) {
        val d = stringExtra ?: ""
        selectedNote = when {
            d.isLocationUrl() -> mutableStateOf(Note(link = stringExtra ?: "", tags = "location"))
            d.isUrl() -> mutableStateOf(Note(link = stringExtra ?: "", tags = "link"))
            else -> mutableStateOf(Note(info = stringExtra ?: "", tags = "text"))
        }
    }
}