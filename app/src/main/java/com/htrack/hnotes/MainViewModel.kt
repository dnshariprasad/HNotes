package com.htrack.hnotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.htrack.hnotes.data.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val noteDao = MyApp.todoDatabase.getNotesDao()

    val noteList: LiveData<List<Note>> = noteDao.getAllNote()

    var selectedNote: Note? = null

    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.addNote(Note(info = note.info))
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.updateNote(note)
        }
    }

    fun deleteTodo(note: Note?) {
        note?.id?.let {
            viewModelScope.launch(Dispatchers.IO) {
                noteDao.deleteNote(it)
            }
        }
    }
}