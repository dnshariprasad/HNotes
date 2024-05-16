package com.htrack.htrack.ui.screen

object Screens {
    const val SCREEN_CREATE_NOTE = "create_note"
    const val SCREEN_NOTE_LIST = "note_list"
}

object NoteTypes {
    const val NOTE_TYPE_TEXT = "text"
    const val NOTE_TYPE_LINK = "link"
    const val NOTE_TYPE_LOCATION = "location"

    fun asList(): List<String> {
        return listOf(NOTE_TYPE_TEXT, NOTE_TYPE_LINK, NOTE_TYPE_LOCATION)
    }
}
