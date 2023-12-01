package com.example.mynotes.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.Data.DoesOb
import com.example.mynotes.Data.DoesRepository
import com.example.mynotes.Data.NotesOb
import com.example.mynotes.Data.NotesRepository
import com.example.mynotes.EditNoteDestination
import com.example.mynotes.EditTareaDestination
import com.example.mynotes.State.NewNoteUiState
import com.example.mynotes.State.NewTareaUiState
import com.example.mynotes.State.NoteUiState
import com.example.mynotes.State.TareaUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditarTareaViewModel (savedStateHandle: SavedStateHandle, private val doesRepository: DoesRepository): ViewModel() {
    private val _uiState= MutableStateFlow(NewTareaUiState())
    val uiState: StateFlow<NewTareaUiState> = _uiState.asStateFlow()

    var itemUiState by mutableStateOf(DoesUiState())
        private set

    private val item: String = checkNotNull(savedStateHandle[EditTareaDestination.tareaIdArg])

    private val itemId: Int = Integer.parseInt(item)
    init {
        viewModelScope.launch {
            itemUiState = doesRepository.getItemStream(itemId)
                .filterNotNull()
                .first()
                .toItemUiState()
        }
    }

    suspend fun updateItem() {
        doesRepository.updateItem(itemUiState.doesDetails.toItem())
    }

    fun updateUiState(doesDetails: DoesDetails) {
        itemUiState =
            DoesUiState(doesDetails = doesDetails)
    }
}