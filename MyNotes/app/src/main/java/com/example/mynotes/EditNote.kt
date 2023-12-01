package com.example.mynotes

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Attachment
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mynotes.ViewModel.NewNoteViewModel
import com.example.mynotes.ViewModel.NoteViewModel
import com.example.mynotes.ui.theme.MyNotesTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.Navigation
import com.example.mynotes.Navigation.Screens
import com.example.mynotes.ViewModel.EditNoteViewModel
import com.example.mynotes.ViewModel.ItemDetails
import kotlinx.coroutines.launch
object EditNoteDestination {
    const val itemIdArg = "itemId"
    val routeWithArgs = "editnote/{$itemIdArg}"
}
class EditNote : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyNotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EditNoteForm(navHostController = rememberNavController()
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun name(
    name: ItemDetails,
    changeName:  (ItemDetails) -> Unit,
    modifier : Modifier=Modifier
){
    TextField(
        value = name.titulo,
        onValueChange = {changeName(name.copy(titulo = it))},
        label = { Text(text = "Nombre Nota")},
        textStyle=MaterialTheme.typography.headlineMedium,

        modifier = modifier
            .fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun content(
    content: ItemDetails,
    changeContent:  (ItemDetails) -> Unit,
    modifier : Modifier=Modifier
){
    TextField(
        value = content.contenido,
        onValueChange = {changeContent(content.copy(contenido = it))},
        label={ Text(text = "Descripcion")},
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteForm(
    navHostController: NavHostController,
    newNoteViewModel: EditNoteViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier) {

    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    Scaffold (
        modifier = Modifier
            .padding(5.dp),
        topBar={
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navHostController.navigate(Screens.NotaScreen.route)}) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                title = {},
                actions = {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                newNoteViewModel.updateItem()
                                navHostController.popBackStack()
                            }
                        }) {
                        Icon(Icons.Filled.Check, contentDescription = "done")
                    }
                }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Add files") },
                icon = { Icon(Icons.Filled.AttachFile, contentDescription = "") },
                onClick = {
                    showBottomSheet = true
                }
            )
        }
    ){contentPadding ->
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                // Sheet content
                val context = LocalContext.current

                //Boton de Fotos
                TextButton(onClick = {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    if (intent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(intent)
                    }
                }) {
                    Row(
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Filled.CameraAlt, contentDescription = "Photo")
                        Text(text = " Foto")
                    }
                }

                //Boton de Video
                TextButton(onClick = {
                    val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
                    if (intent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(intent)
                    }
                }) {
                    Row(
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Filled.Videocam, contentDescription = "Video")
                        Text(text = " Video")
                    }
                }

                //Boton de Audio
                TextButton(onClick = {
                    val intent = Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION)
                    if (intent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(intent)
                    }
                }) {
                    Row(
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Filled.Mic, contentDescription = "Audio")
                        Text(text = " Audio")
                    }
                }

                //Boton de Documentos
                TextButton(onClick = {
                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.type = "*/*"
                    if (intent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(intent)
                    }
                }) {
                    Row(
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Filled.FileOpen, contentDescription = "Document")
                        Text(text = " Documentos")
                    }
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = modifier
                .padding(contentPadding)) {
            name(name = newNoteViewModel.itemUiState.itemDetails, changeName = newNoteViewModel::updateUiState)
            content(content = newNoteViewModel.itemUiState.itemDetails, changeContent = newNoteViewModel::updateUiState)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EditNoteForm() {
    MyNotesTheme {
        Greeting4(navHostController = rememberNavController()
        )
    }
}