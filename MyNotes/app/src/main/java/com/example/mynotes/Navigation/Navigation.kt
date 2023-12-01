package com.example.mynotes.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mynotes.Greeting2
import com.example.mynotes.Greeting3
import com.example.mynotes.Greeting4
import com.example.mynotes.Greeting5
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mynotes.EditNote
import com.example.mynotes.EditNoteDestination
import com.example.mynotes.EditNoteForm
import com.example.mynotes.EditTareaDestination
import com.example.mynotes.EditTareaForm
import com.example.mynotes.ViewModel.NewNoteViewModel

@Composable
fun NavigationScreen() {
    val nav= rememberNavController()
    NavHost(navController = nav, startDestination = Screens.NotaScreen.route){
        composable(route=Screens.NewNotaScreen.route){
            Greeting4(nav);
        }
        composable(route=Screens.NewTareaScreen.route){
            Greeting5(nav);
        }
        composable(route=Screens.TareaScreen.route){
            Greeting3(nav);
        }
        composable(route=Screens.NotaScreen.route){
            Greeting2(nav);
        }
        composable(
            route = EditNoteDestination.routeWithArgs,
            arguments = listOf(navArgument(EditNoteDestination.itemIdArg) {
            })
        ) {
            EditNoteForm(nav)
        }
        composable(
            route = EditTareaDestination.routeWithArgs,
            arguments = listOf(navArgument(EditTareaDestination.tareaIdArg) {
            })
        ) {
            EditTareaForm(nav)
        }
    }
}