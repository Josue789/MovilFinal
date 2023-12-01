package com.example.mynotes.Data

import android.content.Context

interface AppContainer {
    val itemsRepository : NotesRepository
    val doesRepository : DoesRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val itemsRepository: NotesRepository by lazy {
        OfflineNotesRepository(InventoryNotesDatabase.getDatabase(context).itemDao())
    }
    override val doesRepository: DoesRepository by lazy {
        OfflineDoesRepository(InventoryDoesDatabase.getDatabase(context).itemDao())
    }
}