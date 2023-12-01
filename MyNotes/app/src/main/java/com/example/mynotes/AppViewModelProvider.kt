package com.example.mynotes

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mynotes.App.NotesApplication
import com.example.mynotes.ViewModel.NewNoteViewModel
import com.example.mynotes.ViewModel.NewTareaViewModel
import com.example.mynotes.ViewModel.NoteViewModel
import com.example.mynotes.ViewModel.TareaViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {

        initializer {
            NewNoteViewModel(NotesApplication().container.itemsRepository)
        }

        initializer {
            NoteViewModel(NotesApplication().container.itemsRepository)
        }
        initializer {
            NewTareaViewModel(NotesApplication().container.doesRepository)
        }
        initializer {
            TareaViewModel(NotesApplication().container.doesRepository)
        }

    }
}

fun CreationExtras.NotesApplication(): NotesApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NotesApplication)