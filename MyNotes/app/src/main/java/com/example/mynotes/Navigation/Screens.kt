package com.example.mynotes.Navigation

sealed class Screens(
    val route: String
){
    object TareaScreen : Screens(
        route = "tareas_screen"
    )

    object NotaScreen : Screens(
        route = "notas_screen"
    )
}
