package com.example.mynotes.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.Data.DoesOb
import com.example.mynotes.Data.DoesRepository
import com.example.mynotes.Data.NotesOb
import com.example.mynotes.Data.NotesRepository
import com.example.mynotes.State.NoteUiState
import com.example.mynotes.State.TareaUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class TareaViewModel(var itemsRepository: DoesRepository): ViewModel(){
    private val _uiState= MutableStateFlow(TareaUiState())
    val uiState: StateFlow<TareaUiState> = _uiState.asStateFlow()

    val homeUiState: StateFlow<TareaViewModel.HomeUiState> =
        itemsRepository.getAllItemsStream().map{ TareaViewModel.HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TareaViewModel.TIMEOUT_MILLIS),
                initialValue = TareaViewModel.HomeUiState()
            )
    suspend fun eliminarTarea(tarea: DoesOb){
        itemsRepository.deleteItem(tarea)
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
    var input by mutableStateOf("")
        private set

    fun updateSearch(it: String) {
        input = it
    }
    data class HomeUiState(val itemList: List<DoesOb> = listOf())
}