package com.example.mynotes.App

import android.app.Application
import com.example.mynotes.Data.AppContainer
import com.example.mynotes.Data.AppDataContainer

class NotesApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}