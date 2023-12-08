package com.example.mynotes

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mynotes.Multimedia.ComposeProvider
import com.example.mynotes.Multimedia.DialogShowAudioSelected
import com.example.mynotes.Multimedia.DialogShowFileSelected
import com.example.mynotes.Multimedia.DialogShowImageTake
import com.example.mynotes.Multimedia.DialogShowVideoTake
import com.example.mynotes.Navigation.Screens
import com.example.mynotes.ViewModel.ItemDetails
import com.example.mynotes.ViewModel.NewNoteViewModel
import com.example.mynotes.ui.theme.MyNotesTheme
import kotlinx.coroutines.launch


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
                    Greeting4(navHostController = rememberNavController()
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
fun Greeting4(
    navHostController: NavHostController,
    newNoteViewModel: NewNoteViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier) {

    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    // multimedia
    var uri: Uri by remember { mutableStateOf(Uri.EMPTY) }

    // tomar foto INICIO ---------------------------------------------------------------------------

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var listImageUri by remember {
        mutableStateOf(listOf<Uri>())
    }
    var showImage by remember {
        mutableStateOf(false)
    }
    var listImageTemp by remember {
        mutableStateOf("")
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {

            uri.let {
                listImageUri = listImageUri + it // Agrega la Uri a la lista
                listImageTemp += "$it|"
            }
            imageUri = uri
            showImage = !showImage
            newNoteViewModel.uriImages= fromStringList(listImageUri).toString()
        }
    }

    if(showImage){
        DialogShowImageTake(
            onDismiss = { showImage = !showImage },
            imageUri = listImageUri
        )
    }
    // tomar foto FIN ------------------------------------------------------------------------------

    // tomar video INICIO --------------------------------------------------------------------------
    var videoUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var listVideoUri by remember {
        mutableStateOf(listOf<Uri>())
    }
    var showVideo by remember {
        mutableStateOf(false)
    }
    var listVideoTemp by remember {
        mutableStateOf("")
    }

    val videoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CaptureVideo()
    ) { success ->
        if (success) {
            uri.let {
                listVideoUri = listVideoUri + it // Agrega la Uri a la lista
                listVideoTemp += "$it|"
            }
            videoUri = uri
            showVideo = !showVideo
            newNoteViewModel.uriVideos= fromStringList(listVideoUri).toString()
        }
    }
    if(showVideo){

        DialogShowVideoTake(
            onDismiss = { showVideo = !showVideo },
            videoUri = listVideoUri
        )
    }
    // tomar video FIN -----------------------------------------------------------------------------


    // seleccionar audio INICIO ------------------------------------------------------------------
    var audioUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var listAudioUri by remember {
        mutableStateOf(listOf<Uri>())
    }
    var showAudio by remember {
        mutableStateOf(false)
    }

    var listAudioTemp by remember {
        mutableStateOf("")
    }

    val audioPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) { uri: Uri? ->
        uri?.let {
            // Aquí puedes manejar la URI del archivo seleccionado
            listAudioUri = listAudioUri + it // Agrega la Uri del archivo a la lista
            listAudioTemp += "$it|"
        }
        audioUri = uri
        showAudio = !showAudio
        newNoteViewModel.uriAudios= fromStringList(listAudioUri).toString()
    }

    if(showAudio){
        DialogShowAudioSelected(
            onDismiss = { showAudio = !showAudio },
            fileUri = listAudioUri
        )
    }
    // seleccionar audio FIN ---------------------------------------------------------------------

    // seleccionar audio INICIO ------------------------------------------------------------------
    var fileUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var listFileUri by remember {
        mutableStateOf(listOf<Uri>())
    }
    var showFile by remember {
        mutableStateOf(false)
    }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) { uri: Uri? ->
        uri?.let {
            // Aquí puedes manejar la URI del archivo seleccionado
            listFileUri = listFileUri + it // Agrega la Uri del archivo a la lista
        }
        fileUri = uri
        showFile = !showFile
    }

    if(showFile){
        DialogShowFileSelected(
            onDismiss = { showFile = !showFile }
        )
    }
    // seleccionar audio FIN ---------------------------------------------------------------------






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
                                newNoteViewModel.saveItem()
                                navHostController.popBackStack()
                            }
                        }) {
                        Icon(Icons.Filled.Check, contentDescription = "done")
                    }
                }
            )
            Text(text = newNoteViewModel.uriImages)
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
                    uri = ComposeProvider.getImageUri(context)
                    cameraLauncher.launch(uri)
                }) {
                    Row(
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Filled.CameraAlt, contentDescription = "Photo")
                        Text(text = " Foto")
                    }
                }
                //Boton de mostrar Fotos
                TextButton(onClick = {
                    //Mostrar foto

                }) {
                    Row(
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Filled.CameraAlt, contentDescription = "Photo")
                        Text(text = "Mostrar Foto")
                    }
                }

                //Boton de Video
                TextButton(onClick = {
                    uri = ComposeProvider.getImageUri(context)
                    videoLauncher.launch(uri)
                }) {
                    Row(
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Filled.Videocam, contentDescription = "Video")
                        Text(text = " Video")
                    }
                }
                //Boton de mostrar Video
                TextButton(onClick = {

                }) {
                    Row(
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Filled.Videocam, contentDescription = "Video")
                        Text(text = "Mostrar Video")
                    }
                }

                //Boton de Audio
                TextButton(onClick = {
                    audioPickerLauncher.launch("audio/*")
                }) {
                    Row(
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Filled.Mic, contentDescription = "Audio")
                        Text(text = " Audio")
                    }
                }
                //Boton de mostrar Audio
                TextButton(onClick = {
                    //Mostrar Audios
                }) {
                    Row(
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Filled.Mic, contentDescription = "Audio")
                        Text(text = "Mostrar Audio")
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

fun PrepareUris(content: ItemDetails, changeContent:  (ItemDetails) -> Unit, lists: List<String?>) {
    changeContent(content.copy(contenido = content.contenido+
            "->Fotos "+lists[0]+
            "->Videos "+lists[1]+
            "->Audios "+lists[2]))
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    MyNotesTheme {
        Greeting4(navHostController = rememberNavController()
        )
    }
}