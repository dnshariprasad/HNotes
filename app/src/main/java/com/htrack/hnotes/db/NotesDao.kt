package com.htrack.hnotes.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.htrack.hnotes.data.Note

@Dao
interface NotesDao {

    @Query("SELECT * FROM Note ORDER BY id DESC")
    fun getAllNote(): LiveData<List<Note>>

    @Insert
    fun addNote(note: Note)

    @Update
    fun updateNote(note: Note): Int

    @Query("Delete FROM Note where id = :id")
    fun deleteNote(id: Int)
}