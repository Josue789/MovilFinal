package com.example.mynotes

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.sharp.List
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import java.time.format.TextStyle

class Tareas : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyNotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting3()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Greeting3(modifier: Modifier = Modifier) {
    var Search by remember {
        mutableStateOf("Search")
    }
    Scaffold(
        topBar = {
            Column(
                modifier = modifier
                    .padding(5.dp)

            ) {
                Text(
                    text = "Tareas",
                )
                TextField(
                    value = "",
                    label = {"Search"},
                    onValueChange = {Search = it},
                    modifier = modifier
                        .fillMaxWidth())
            }
        },
        bottomBar  ={
            BottomAppBar(
                actions = {
                    IconButton(
                        onClick = { /* do something */ },
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(80.dp)) {
                        Column {
                            Icon(Icons.Filled.Create,
                                contentDescription = "Notas"
                            )
                            Text(text = "Notas")
                        }

                    }
                    IconButton(
                        onClick = { /* do something */ },
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(80.dp)
                    ) {
                        Column {
                            Icon(
                                Icons.Filled.List,
                                contentDescription = "Tareas",
                            )
                            Text(text = "Tareas")
                        }
                    }

                },




                )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {  }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ){innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = modifier
                .padding(innerPadding)

        ){
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .height(100.dp)

            ){
                Column {
                    Row(
                        modifier = modifier
                            .height(50.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically

                    ){
                        Text(text = "Title")
                    }
                    Row {
                        Icon(Icons.Filled.DateRange, contentDescription = "FchLim")
                        Text(text = "dd/mm/aaaa")
                    }
                }
            }
            Spacer(modifier = modifier.fillMaxWidth())
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppTareasBarSearch(modifier: Modifier=Modifier){
    var search by remember {
        mutableStateOf("Busqueda")
    }
    CenterAlignedTopAppBar(
        title = {
            Column(

                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.padding(top = 24.dp)
            ){
                Text(
                    text = "Notas",
                    modifier = modifier
                        .size(height = 50.dp, width = 50.dp)
                )
                TextField(
                    value = "",
                    onValueChange = {search=it},
                    label = {
                        Text(text = "Search")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
            }
        },
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    MyNotesTheme {
        Greeting3()
    }
}