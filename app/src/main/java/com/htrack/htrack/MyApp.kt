package com.htrack.htrack

import android.app.Application
import androidx.room.Room
import com.htrack.htrack.db.NotesDb
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
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