package com.example.mynotes.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mynotes.State.NewNoteUiState
import com.example.mynotes.State.NoteUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class NewNoteViewModel: ViewModel(){
    private val _uiState= MutableStateFlow(NewNoteUiState())
    val uiState: StateFlow<NewNoteUiState> = _uiState.asStateFlow()

    var inputName by mutableStateOf("")
        private set

    fun updateName(it: String) {
        inputName = it
    }

    var inputContent by mutableStateOf("")
        private set

    fun updateContent(it: String) {
        inputContent = it
    }
}