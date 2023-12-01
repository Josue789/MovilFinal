package com.example.mynotes.ViewModel

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.Data.DoesRepository
import com.example.mynotes.Data.NotesOb
import com.example.mynotes.Data.NotesRepository
import com.example.mynotes.EditNoteDestination.itemIdArg
import com.example.mynotes.State.NewNoteUiState
import com.example.mynotes.State.NoteUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditNoteViewModel(savedStateHandle: SavedStateHandle, private val itemsRepository: NotesRepository): ViewModel(){
    private val _uiState= MutableStateFlow(NewNoteUiState())
    val uiState: StateFlow<NewNoteUiState> = _uiState.asStateFlow()
    var itemUiState by mutableStateOf(ItemUiState())
        private set

    private val item: String = checkNotNull(savedStateHandle[itemIdArg])

    private val itemId: Int = Integer.parseInt(item)
    init {
        viewModelScope.launch {
            itemUiState = itemsRepository.getItemStream(itemId)
                .filterNotNull()
                .first()
                .toItemUiState()
        }
    }
    suspend fun updateItem() {
        itemsRepository.updateItem(itemUiState.itemDetails.toItem())
    }

    fun updateUiState(itemDetails: ItemDetails) {
        itemUiState =
            ItemUiState(itemDetails = itemDetails)
    }
}