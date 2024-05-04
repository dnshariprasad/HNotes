package com.htrack.hnotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.htrack.hnotes.data.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val noteDao = MyApp.todoDatabase.getNotesDao()

    val noteList: LiveData<List<Note>> = noteDao.getAllTodo()


    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.addTodo(Note(info = note.info))
        }
    }

    fun deleteTodo(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.deleteTodo(note.id)
        }
    }
}