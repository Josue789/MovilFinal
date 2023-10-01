package com.example.mynotes

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mynotes.ui.theme.MyNotesTheme

class NewNote : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyNotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting4("Android")
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting4(name: String, modifier: Modifier = Modifier) {
    var title by remember{ mutableStateOf("title") }
    var description by remember{ mutableStateOf("description") }
    Scaffold (
        modifier = Modifier
            .padding(5.dp),
        topBar={
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = "Nueva nota",
                    onValueChange ={title = it},
                    label = {"Nueva nota"},
                    textStyle=MaterialTheme.typography.headlineMedium,
                    modifier = modifier
                        .fillMaxWidth()
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {  }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ){innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = modifier
                .padding(innerPadding)) {
            TextField(
                value = "",
                onValueChange ={description = it},
                label={"Descripcion"},
                modifier = modifier
                    .fillMaxWidth()
                    .height(500.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    MyNotesTheme {
        Greeting4("Android")
    }
}