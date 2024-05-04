package com.htrack.hnotes.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.htrack.hnotes.data.Note

@Dao
interface NotesDao {

    @Query("SELECT * FROM Note")
    fun getAllTodo(): LiveData<List<Note>>

    @Insert
    fun addTodo(note: Note)

    @Query("Delete FROM Note where id = :id")
    fun deleteTodo(id: Int)

}