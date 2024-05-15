package com.htrack.hnotes.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.htrack.hnotes.data.models.Note
import com.htrack.hnotes.isLocationUrl
import com.htrack.hnotes.isUrl
import com.htrack.hnotes.ui.screen.NoteTypes.NOTE_TYPE_LINK
import com.htrack.hnotes.ui.screen.NoteTypes.NOTE_TYPE_LOCATION
import com.htrack.hnotes.ui.screen.NoteTypes.NOTE_TYPE_TEXT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading get() = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            delay(500)
            _isLoading.value = false
        }
    }

    fun handleShareData(stringExtra: String?): Note {
        val d = stringExtra ?: ""
        return when {
            d.isLocationUrl() ->
                Note(
                    link = stringExtra ?: "",
                    type = NOTE_TYPE_LOCATION
                )

            d.isUrl() -> Note(link = stringExtra ?: "", type = NOTE_TYPE_LINK)
            else -> Note(info = stringExtra ?: "", type = NOTE_TYPE_TEXT)
        }
    }
}