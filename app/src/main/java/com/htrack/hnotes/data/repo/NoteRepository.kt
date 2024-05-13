package com.htrack.hnotes.data.repo

import com.htrack.hnotes.data.models.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun createNotes(note: Note): Flow<Any>
    fun updateNotes(note: Note): Flow<Any>
}