package com.htrack.hnotes.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.htrack.hnotes.data.Note

@Database(entities = [Note::class], version = 1)
abstract class NotesDb : RoomDatabase() {
    companion object {
        const val NAME = "hnotes"
    }

    abstract fun getNotesDao(): NotesDao
}