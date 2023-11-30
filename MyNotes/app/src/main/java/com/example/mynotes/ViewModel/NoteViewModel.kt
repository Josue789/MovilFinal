package com.example.mynotes.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.Data.NotesOb
import com.example.mynotes.Data.NotesRepository
import com.example.mynotes.State.NoteUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class NoteViewModel(var itemsRepository: NotesRepository): ViewModel() {

    private val _uiState= MutableStateFlow(NoteUiState())
    val uiState: StateFlow<NoteUiState> = _uiState.asStateFlow()
    val homeUiState: StateFlow<HomeUiState> =
        itemsRepository.getAllItemsStream().map{HomeUiState(it)}
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )
    suspend fun eliminarNota(notes: NotesOb){
        itemsRepository.deleteItem(notes)
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    var input by mutableStateOf("")
        private set

    fun updateSearch(it: String) {
        input = it
    }
    data class HomeUiState(val itemList: List<NotesOb> = listOf())
}