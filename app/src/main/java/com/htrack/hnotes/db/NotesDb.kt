package com.htrack.hnotes.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.htrack.hnotes.data.models.Note

@Database(entities = [Note::class], version = 1)
abstract class NotesDb : RoomDatabase() {
    companion object {
        const val NAME = "hnotes"
    }

    abstract fun getNotesDao(): NotesDao
}