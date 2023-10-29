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

    object NewNotaScreen : Screens(
        route = "new_notas_screen"
    )

    object NewTareaScreen : Screens(
        route = "new_tarea_screen"
    )

}
