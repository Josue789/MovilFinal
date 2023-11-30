package com.example.mynotes

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.Entity
import com.example.mynotes.Data.InventoryNotesDatabase
import com.example.mynotes.Data.NotesOb
import com.example.mynotes.Navigation.Screens
import com.example.mynotes.ViewModel.NoteViewModel
import com.example.mynotes.ui.theme.MyNotesTheme
import kotlinx.coroutines.launch

@Entity
class Notes : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyNotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting2(navHostController = rememberNavController())
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Greeting2(navHostController: NavHostController, noteViewModel:NoteViewModel = viewModel(factory = AppViewModelProvider.Factory),
              modifier: Modifier = Modifier) {
    val homeUiState by noteViewModel.homeUiState.collectAsState()
    val NoteUiState by noteViewModel.uiState.collectAsState()

    val db= InventoryNotesDatabase.getDatabase(LocalContext.current)
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "NOTAS",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            )
        },
        bottomBar  ={
            BottomAppBar(

                actions = {
                    Row(horizontalArrangement = Arrangement.Center){
                        IconButton(
                            onClick = { /* do something */ },
                            modifier = Modifier
                                .align(CenterVertically)
                                .fillMaxHeight()
                                .width(80.dp))

                        {
                            Column(verticalArrangement = Arrangement.Center) {
                                Icon(Icons.Filled.Create,
                                    contentDescription = "Notas"
                                )
                                Text(text = "Notas")
                            }

                        }
                        IconButton(
                            onClick = {navHostController.navigate(Screens.TareaScreen.route)},
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(80.dp))
                        {
                            Column {
                                Icon(
                                    Icons.Filled.List,
                                    contentDescription = "Tareas",
                                )
                                Text(text = "Tareas")
                            }
                        }
                        BarraBusqueda(search = noteViewModel.input,
                            onSearch= {noteViewModel.updateSearch(it) })
                    }

                },
            )


        },
        content =  {
            contenidoPrincipal(contentPadding = it, itemList = homeUiState.itemList, viewModel=noteViewModel, navController=navHostController)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navHostController.navigate(Screens.NewNotaScreen.route) }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun contenidoPrincipal(contentPadding: PaddingValues = PaddingValues(0.dp), itemList: List<NotesOb>, viewModel: NoteViewModel, navController: NavController){
    val itemList: List<NotesOb> = itemList
    if (itemList.isEmpty()) {

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No hay ninguna nota agregada",
                style = MaterialTheme.typography.titleLarge,
                )
        }
    } else {
        Column(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier.padding(top = 12.dp),
                contentPadding = contentPadding
            ){
                items(items = itemList, key = { it.id }) {
                        item -> tarjetaNota(item, viewModel, navController)
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
private fun BarraBusqueda(search: String,onSearch: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = search,
            onValueChange = onSearch,
            label = {
                Text(text = "Buscar")
            },
            trailingIcon = {
                IconButton(onClick = {
                    keyboardController?.hide()
                }) {
                    Icon(imageVector = Icons.Outlined.Clear, contentDescription = null)
                }
            },/*
            supportingText = {
                Text(text = "Nombre o descripcion de la nota")
            },*/
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            modifier = Modifier
                .fillMaxWidth(0.85f)
        )
    }
}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarSearch(modifier: Modifier=Modifier){
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun tarjetaNota(item: NotesOb,viewModel: NoteViewModel, navController: NavController) {
    var eliminarConfirmaciónrequerida by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { navController.navigate(Screens.NewNotaScreen.route,)  }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .sizeIn(minHeight = 72.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    overflow= TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.displaySmall

                )
                Text(
                    text = item.description,
                    overflow= TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                IconButton(
                    onClick = {
                        eliminarConfirmaciónrequerida = true
                    },
                    modifier = Modifier
                        .align(Alignment.Center)
                ) {
                    Icon(painter = painterResource(id = R.drawable.trash),
                        contentDescription = "Borrar",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            if (eliminarConfirmaciónrequerida) {
                mostrarDialogoEliminacion(
                    confirmarEliminacion = {
                        eliminarConfirmaciónrequerida = false
                        coroutineScope.launch {
                            viewModel.eliminarNota(item)
                        }
                    },
                    cancelarEliminacion = { eliminarConfirmaciónrequerida = false },
                )
            }
        }
    }
}
@Composable
private fun mostrarDialogoEliminacion(
    confirmarEliminacion: () -> Unit,
    cancelarEliminacion: () -> Unit,
) {
    AlertDialog(onDismissRequest = {  },
        title = { Text("Confirmar eliminación") },
        text = { Text("¿Estás seguro de que deseas eliminar esta nota?") },
        modifier = Modifier.padding(16.dp),
        dismissButton = {
            TextButton(onClick = cancelarEliminacion  ) {
                Text(text = "Cancelar")
            }
        },
        confirmButton = {
            TextButton(onClick = confirmarEliminacion ) {
                Text(text = "Si, eliminar")
            }
        })
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {

    MyNotesTheme {
        Greeting2(navHostController = rememberNavController())
    }
}