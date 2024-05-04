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


    fun addNote(info: String) {
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.addTodo(Note(info = info))
        }
    }

    fun deleteTodo(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.deleteTodo(id)
        }
    }
}