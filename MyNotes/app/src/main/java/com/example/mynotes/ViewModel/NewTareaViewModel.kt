package com.example.mynotes.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.mynotes.State.NoteUiState
import com.example.mynotes.State.TareaUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NewTareaViewModel {
    private val _uiState= MutableStateFlow(TareaUiState())
    val uiState: StateFlow<TareaUiState> = _uiState.asStateFlow()

    var name by mutableStateOf("")
        private set

    fun updateName(it: String) {
        name = it
    }
    var start by mutableStateOf("")
        private set

    fun updateStart(it: String) {
        start = it
    }
    var end by mutableStateOf("")
        private set

    fun updateEnd(it: String) {
        end = it
    }
    var content by mutableStateOf("")
        private set

    fun updateContent(it: String) {
        content = it
    }

}