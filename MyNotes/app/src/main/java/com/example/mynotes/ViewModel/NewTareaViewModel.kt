package com.example.mynotes.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mynotes.Data.DoesOb
import com.example.mynotes.Data.DoesRepository
import com.example.mynotes.Data.NotesOb
import com.example.mynotes.Data.NotesRepository
import com.example.mynotes.State.NewNoteUiState
import com.example.mynotes.State.NewTareaUiState
import com.example.mynotes.State.NoteUiState
import com.example.mynotes.State.TareaUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NewTareaViewModel (private val itemsRepository: DoesRepository): ViewModel() {
    private val _uiState= MutableStateFlow(NewTareaUiState())
    val uiState: StateFlow<NewTareaUiState> = _uiState.asStateFlow()

    var doesUiState by mutableStateOf(DoesUiState())
        private set
    //Uris en texto
    var uriImages by mutableStateOf(String())
    var uriVideos by mutableStateOf(String())
    var uriAudios by mutableStateOf(String())
    suspend fun saveItem() {
        /*
        doesUiState.doesDetails.toItem().uriAudios=uriAudios;
        doesUiState.doesDetails.toItem().uriAudios=uriAudios;
        doesUiState.doesDetails.toItem().uriAudios=uriAudios;
         */
        val tareaN = doesUiState.doesDetails.copy(images = uriImages, videos = uriVideos, audios = uriAudios).toItem()
        itemsRepository.insertItem(tareaN)
    }
    fun updateUiState(doesDetails: DoesDetails) {
        doesUiState =
            DoesUiState(doesDetails = doesDetails)
    }

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
fun DoesOb.toItemUiState(): DoesUiState = DoesUiState(
    doesDetails = this.toItemDetails()
)
fun DoesOb.toItemDetails(): DoesDetails = DoesDetails(
    id = id,
    titulo = title,
    contenido = description,
    start = date,
    end = dateEnd
)
fun DoesDetails.toItem(): DoesOb = DoesOb(
    id = id,
    title = titulo,
    description = contenido,
    date = start,
    dateEnd = end,
    uriImages = images,
    uriVideos = videos,
    uriAudios = audios

)
data class DoesUiState(
    val doesDetails: DoesDetails = DoesDetails(),
)
data class DoesDetails(
    val id: Int = 0,
    val titulo: String = "",
    val contenido: String = "",
    val start: String="",
    val end: String="",
    val images: String="",
    val videos: String="",
    val audios: String=""
)