package com.example.mynotes.ViewModel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.Data.DoesRepository
import com.example.mynotes.Data.NotesOb
import com.example.mynotes.Data.NotesRepository
import com.example.mynotes.EditNoteDestination.itemIdArg
import com.example.mynotes.State.NewNoteUiState
import com.example.mynotes.State.NoteUiState
import com.example.mynotes.fromStringList
import com.example.mynotes.toStringList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class   EditNoteViewModel(savedStateHandle: SavedStateHandle, private val itemsRepository: NotesRepository): ViewModel(){
    private val _uiState= MutableStateFlow(NewNoteUiState())
    val uiState: StateFlow<NewNoteUiState> = _uiState.asStateFlow()
    var itemUiState by mutableStateOf(ItemUiState())
        private set

    private val item: String = checkNotNull(savedStateHandle[itemIdArg])

    //Uris en texto
    var uriImages by mutableStateOf(String())
    var uriVideos by mutableStateOf(String())
    var uriAudios by mutableStateOf(String())

    var uriImagesCargadas by mutableStateOf(listOf<Uri>())
    var uriVideosCargadas by mutableStateOf(listOf<Uri>())
    var uriAudiosCargadas by mutableStateOf(listOf<Uri>())

    private val itemId: Int = Integer.parseInt(item)
    init {
        viewModelScope.launch {
            itemUiState = itemsRepository.getItemStream(itemId)
                .filterNotNull()
                .first()
                .toItemUiState()
            Log.d("Firulais", itemUiState.itemDetails.contenido)
            uriImagesCargadas = toStringList(itemUiState.itemDetails.toItem().uriImages)?: listOf<Uri>();
            uriAudiosCargadas = toStringList(itemUiState.itemDetails.toItem().uriAudios)?: listOf<Uri>();
            uriVideosCargadas = toStringList(itemUiState.itemDetails.toItem().uriVideos)?: listOf<Uri>();
        }


    }
    suspend fun updateItem() {

        /*
        itemUiState.itemDetails.toItem().uriAudios=uriAudios;
        itemUiState.itemDetails.toItem().uriAudios=uriAudios;
        itemUiState.itemDetails.toItem().uriAudios=uriAudios;
         */
        val tareaE = itemUiState.itemDetails.copy(uriImages = uriImages, uriVideos = uriVideos, uriAudios = uriAudios).toItem()
        itemsRepository.updateItem(tareaE)
    }

    fun updateUiState(itemDetails: ItemDetails) {
        itemUiState =
            ItemUiState(itemDetails = itemDetails)
    }
}