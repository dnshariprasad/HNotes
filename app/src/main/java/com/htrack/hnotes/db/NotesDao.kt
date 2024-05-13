package com.htrack.hnotes.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.htrack.hnotes.data.models.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM Note ORDER BY id DESC")
    fun getNotes(): Flow<List<Note>>

    @Insert
    fun addNote(note: Note)

    @Update
    fun updateNote(note: Note): Int

    @Query("Delete FROM Note where id = :id")
    fun deleteNote(id: Int)
}