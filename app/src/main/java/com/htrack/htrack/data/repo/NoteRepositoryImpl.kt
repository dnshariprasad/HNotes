package com.htrack.htrack.data.repo

import com.htrack.htrack.MyApp
import com.htrack.htrack.data.models.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class NoteRepositoryImpl : NoteRepository {
    val noteDao = MyApp.todoDatabase.getNotesDao()

    val notes: Flow<List<Note>> = noteDao.getNotes()


    override fun createNotes(note: Note): Flow<Any> = flow {
        withContext(Dispatchers.IO) {
            noteDao.getNotes()
        }
    }

    override fun updateNotes(note: Note): Flow<Any> = flow {
        withContext(Dispatchers.IO) {
            noteDao.updateNote(note)
        }
    }
}