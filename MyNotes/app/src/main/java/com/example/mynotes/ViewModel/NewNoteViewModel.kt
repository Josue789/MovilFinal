package com.example.mynotes.ViewModel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mynotes.Data.NotesOb
import com.example.mynotes.Data.NotesRepository
import com.example.mynotes.State.NewNoteUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NewNoteViewModel(private val itemsRepository: NotesRepository): ViewModel(){
    private val _uiState= MutableStateFlow(NewNoteUiState())
    val uiState: StateFlow<NewNoteUiState> = _uiState.asStateFlow()
    var itemUiState by mutableStateOf(ItemUiState())
        private set
    //Uris en texto
    var uriImages by mutableStateOf(String())
    var uriVideos by mutableStateOf(String())
    var uriAudios by mutableStateOf(String())
    suspend fun saveItem() {
        /*
        itemUiState.itemDetails.toItem().uriAudios=uriAudios;
        itemUiState.itemDetails.toItem().uriAudios=uriAudios;
        itemUiState.itemDetails.toItem().uriAudios=uriAudios;
        */
        val nota = itemUiState.itemDetails.copy(uriImages=uriImages, uriVideos = uriVideos, uriAudios = uriAudios).toItem()
        itemsRepository.insertItem(nota)
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

}
fun NotesOb.toItemUiState(): ItemUiState = ItemUiState(
    itemDetails = this.toItemDetails()
)
fun NotesOb.toItemDetails(): ItemDetails = ItemDetails(
    id = id,
    titulo = title,
    contenido = description,
    uriImages = uriImages,
    uriAudios = uriAudios,
    uriVideos = uriVideos

)
fun ItemDetails.toItem(): NotesOb = NotesOb(
    id = id,
    title = titulo,
    description = contenido,
    uriImages = uriImages,
    uriVideos =  uriVideos,
    uriAudios =  uriAudios

)
data class ItemUiState(
    val itemDetails: ItemDetails = ItemDetails()
)
data class ItemDetails(
    val id: Int = 0,
    val titulo: String = "",
    val contenido: String = "",
    val uriImages: String = "",
    val uriVideos: String = "",
    val uriAudios: String = ""
)