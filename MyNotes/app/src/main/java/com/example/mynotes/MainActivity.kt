package com.example.mynotes

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mynotes.Navigation.NavigationScreen
import com.example.mynotes.ViewModel.TareaViewModel
import com.example.mynotes.ui.theme.MyNotesTheme
import java.util.Calendar

class MainActivity : ComponentActivity() {


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column{

    }
}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyNotesTheme {
                // A surface container using the 'background' color from the theme
                Surface() {
                    NavigationScreen()

                }
            }

        }

    }

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyNotesTheme {
        Greeting("Android")
    }
}}