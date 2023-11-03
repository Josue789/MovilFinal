package com.example.mynotes.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mynotes.Greeting2
import com.example.mynotes.Greeting3
import com.example.mynotes.Greeting4
import com.example.mynotes.Greeting5
import androidx.navigation.compose.rememberNavController
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
    }
}