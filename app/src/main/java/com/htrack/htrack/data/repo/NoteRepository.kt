package com.htrack.htrack.data.repo

import com.htrack.htrack.data.models.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun createNotes(note: Note): Flow<Any>
    fun updateNotes(note: Note): Flow<Any>
}