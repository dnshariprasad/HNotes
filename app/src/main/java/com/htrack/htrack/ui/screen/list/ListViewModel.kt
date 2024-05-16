package com.htrack.htrack.ui.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.htrack.htrack.data.models.Note
import com.htrack.htrack.data.repo.NoteRepositoryImpl
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ListViewModel : ViewModel() {
    var repository: NoteRepositoryImpl = NoteRepositoryImpl()
    val notes: StateFlow<List<Note>> = repository.notes
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}

