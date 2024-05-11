package com.htrack.hnotes

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.htrack.hnotes.data.Note
import com.htrack.hnotes.ui.screen.NoteTypes.NOTE_TYPE_LINK
import com.htrack.hnotes.ui.screen.NoteTypes.NOTE_TYPE_LOCATION
import com.htrack.hnotes.ui.screen.NoteTypes.NOTE_TYPE_TEXT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading get() = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            delay(500)
            _isLoading.value = false
        }
    }
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
            d.isLocationUrl() -> mutableStateOf(Note(link = stringExtra ?: "", type = NOTE_TYPE_LOCATION))
            d.isUrl() -> mutableStateOf(Note(link = stringExtra ?: "", type = NOTE_TYPE_LINK))
            else -> mutableStateOf(Note(info = stringExtra ?: "", type = NOTE_TYPE_TEXT))
        }
    }
}