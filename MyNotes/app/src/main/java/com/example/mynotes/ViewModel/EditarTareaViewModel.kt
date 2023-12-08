package com.example.mynotes.ViewModel

import android.net.Uri
import android.util.Log
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
import com.example.mynotes.toStringList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditarTareaViewModel (savedStateHandle: SavedStateHandle, private val doesRepository: DoesRepository): ViewModel() {
    private val _uiState= MutableStateFlow(NewTareaUiState())
    val uiState: StateFlow<NewTareaUiState> = _uiState.asStateFlow()

    //Uris en texto
    var uriImages by mutableStateOf(String())
    var uriVideos by mutableStateOf(String())
    var uriAudios by mutableStateOf(String())

    var uriImagesCargadas by mutableStateOf(listOf<Uri>())
    var uriVideosCargadas by mutableStateOf(listOf<Uri>())
    var uriAudiosCargadas by mutableStateOf(listOf<Uri>())

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

            uriImagesCargadas= toStringList(itemUiState.doesDetails.toItem().uriImages)?: listOf<Uri>();
            //Log.d("PERRO Cargado", uriImagesCargadas.toString())
            uriVideosCargadas= toStringList(itemUiState.doesDetails.toItem().uriVideos)?: listOf<Uri>();
            uriAudiosCargadas= toStringList(itemUiState.doesDetails.toItem().uriAudios)?: listOf<Uri>();
        }


    }

    suspend fun updateItem() {
        /*
        itemUiState.doesDetails.toItem().uriAudios=uriAudios;
        itemUiState.doesDetails.toItem().uriAudios=uriAudios;
        itemUiState.doesDetails.toItem().uriAudios=uriAudios;
         */
        val tareaE = itemUiState.doesDetails.copy(images = uriImages, videos = uriVideos, audios = uriAudios).toItem()
        doesRepository.updateItem(tareaE)
    }

    fun updateUiState(doesDetails: DoesDetails) {
        itemUiState =
            DoesUiState(doesDetails = doesDetails)
    }
}