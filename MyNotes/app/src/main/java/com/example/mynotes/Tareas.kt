package com.example.mynotes

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.material3.SearchBar
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
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
import com.example.mynotes.Data.DoesOb
import com.example.mynotes.Data.InventoryDoesDatabase
import com.example.mynotes.Navigation.Screens
import com.example.mynotes.ViewModel.TareaViewModel
import com.example.mynotes.ui.theme.MyNotesTheme
import kotlinx.coroutines.launch

@Entity
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
                    Greeting3(navHostController = rememberNavController())
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Greeting3(navHostController: NavHostController, tareaViewModel: TareaViewModel = viewModel(factory = AppViewModelProvider.Factory),
              modifier: Modifier = Modifier) {
    val homeUiState by tareaViewModel.homeUiState.collectAsState()
    val tareaUiState by tareaViewModel.uiState.collectAsState()

    val db = InventoryDoesDatabase.getDatabase(LocalContext.current)
    //val listItems = db.itemDao().getAllItems()
    val context = LocalContext.current
    var query by remember { mutableStateOf("") }

    Scaffold(
        topBar = {

            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "TAREAS",
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
                            onClick = { navHostController.navigate(Screens.NotaScreen.route) },
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(80.dp))
                        {
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
                        var active by remember { mutableStateOf(false) }
                        SearchBar(
                            query = query,
                            onQueryChange = { query = tareaViewModel.updateSearch(it)},
                            onSearch = {
                                Toast.makeText(context, query, Toast.LENGTH_SHORT).show()
                                active=false },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text(text = "Buscar...") },
                            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Buscar") },
                            active = active,
                            onActiveChange = { active = it }
                        ) {
                            val searchResultUiState = tareaViewModel.search(homeUiState.itemList, query)
                            contenidoPrincipalTareas(itemList = homeUiState.itemList, viewModel =tareaViewModel, navController =navHostController)

                        }
                    }
                },
            )
        },
        content =  {
            if(query == ""){
                contenidoPrincipalTareas(contentPadding = it, itemList = homeUiState.itemList, viewModel =tareaViewModel, navController =navHostController)
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navHostController.navigate(Screens.NewTareaScreen.route) }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    )
}


@Composable
fun contenidoPrincipalTareas(
    contentPadding: PaddingValues = PaddingValues(0.dp),
    itemList: List<DoesOb>,
    viewModel: TareaViewModel,
    navController: NavController){

    val itemList: List<DoesOb> = itemList
    if (itemList.isEmpty()){
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

    }else{
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
                        item -> tarjetaTarea(item, viewModel, navController)
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun tarjetaTarea(item: DoesOb,viewModel: TareaViewModel, navController: NavController) {
    var eliminarConfirmaciónrequerida by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { navController.navigate("edittarea/${item.id}") }
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
                Text(
                    text = "Vence: "+ item.date,
                    overflow= TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Recordar: "+ item.dateEnd,
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
                            viewModel.eliminarTarea(item)
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
        text = { Text("¿Estás seguro de que deseas eliminar esta tarea?") },
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
        Greeting3(navHostController = rememberNavController())
    }
}