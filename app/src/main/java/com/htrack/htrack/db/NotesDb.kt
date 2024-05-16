package com.htrack.htrack.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.htrack.htrack.data.models.Note

@Database(entities = [Note::class], version = 1)
abstract class NotesDb : RoomDatabase() {
    companion object {
        const val NAME = "htrack"
    }

    abstract fun getNotesDao(): NotesDao
}