package com.example.mynotes.ViewModel

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mynotes.Data.NotesOb
import com.example.mynotes.Data.NotesRepository
import com.example.mynotes.State.NewNoteUiState
import com.example.mynotes.State.NoteUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NewNoteViewModel(private val itemsRepository: NotesRepository): ViewModel(){
    private val _uiState= MutableStateFlow(NewNoteUiState())

    val uiState: StateFlow<NewNoteUiState> = _uiState.asStateFlow()
    var itemUiState by mutableStateOf(ItemUiState())
        private set
    suspend fun saveItem() {
        itemsRepository.insertItem(itemUiState.itemDetails.toItem())
    }

    fun updateUiState(itemDetails: ItemDetails) {
        itemUiState =
            ItemUiState(itemDetails = itemDetails)
    }
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

    data class ItemUiState(
        val itemDetails: ItemDetails = ItemDetails()
    )

    fun ItemDetails.toItem(): NotesOb = NotesOb(
        id = id,
        title = titulo,
        description = contenido
    )
    data class ItemDetails(
        val id: Int = 0,
        val titulo: String = "",
        val contenido: String = ""
    )
}