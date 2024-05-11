package com.htrack.hnotes

import android.app.Application
import androidx.room.Room
import com.htrack.hnotes.db.NotesDb

class MyApp : Application() {
    companion object {
        lateinit var todoDatabase: NotesDb
    }

    override fun onCreate() {
        super.onCreate()
        todoDatabase = Room.databaseBuilder(
            applicationContext,
            NotesDb::class.java,
            NotesDb.NAME
        ).build()
    }
}