package com.example.mynotes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import androidx.room.TypeConverter
import com.example.mynotes.Multimedia.ComposeProvider
import com.example.mynotes.Multimedia.DialogShowAudioSelected
import com.example.mynotes.Multimedia.DialogShowFileSelected
import com.example.mynotes.Multimedia.DialogShowImageTake
import com.example.mynotes.Multimedia.DialogShowVideoTake
import com.example.mynotes.Navigation.Screens
import com.example.mynotes.ViewModel.EditNoteViewModel
import com.example.mynotes.ViewModel.ItemDetails
import com.example.mynotes.ui.theme.MyNotesTheme
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

    // multimedia
    var uri: Uri by remember { mutableStateOf(Uri.EMPTY) }

    // tomar foto INICIO ---------------------------------------------------------------------------

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var listImageUri by remember {
        mutableStateOf(newNoteViewModel.uriImagesCargadas)
    }
    var showImage by remember {
        mutableStateOf(false)
    }
    var firstImage by remember {
        mutableStateOf(true)
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
            showImage = true

            newNoteViewModel.uriImages= fromStringList(listImageUri).toString()
        }
    }

    if(showImage){
        if(firstImage){
            listImageUri+=newNoteViewModel.uriImagesCargadas;
            firstImage=false;
        }
        DialogShowImageTake(
            onDismiss = {showImage = false },
            imageUri = listImageUri)

    }
    // tomar foto FIN ------------------------------------------------------------------------------

    // tomar video INICIO --------------------------------------------------------------------------
    var videoUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var listVideoUri by remember {
        mutableStateOf(newNoteViewModel.uriVideosCargadas)
    }
    var showVideo by remember {
        mutableStateOf(false)
    }
    var firstVideo by remember {
        mutableStateOf(true)
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
            showVideo = true
            newNoteViewModel.uriVideos= fromStringList(listVideoUri).toString()
        }
    }
    if(showVideo){
        if(firstVideo){
            listVideoUri+=newNoteViewModel.uriVideosCargadas;
            firstVideo=false;
        }
        DialogShowVideoTake(
            onDismiss = { showVideo = false },
            videoUri = listVideoUri
        )

    }
    // tomar video FIN -----------------------------------------------------------------------------


    // seleccionar audio INICIO ------------------------------------------------------------------
    var audioUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var listAudioUri by remember {
        mutableStateOf(newNoteViewModel.uriAudiosCargadas)
    }
    var showAudio by remember {
        mutableStateOf(false)
    }

    var firstAudio by remember {
        mutableStateOf(true)
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
        showAudio = true
        newNoteViewModel.uriAudios= fromStringList(listAudioUri).toString()
    }

    if(showAudio){
        if(firstAudio){
            listAudioUri+=newNoteViewModel.uriAudiosCargadas;
            firstAudio = false;
        }
        DialogShowAudioSelected(
            onDismiss = { showAudio = false },
            fileUri = listAudioUri
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
                var pClicks by remember {
                    mutableStateOf(0)
                }
                TextButton(onClick = {
                    pClicks ++;
                    if(pClicks==1){
                        showImage=true;
                    }else{
                        pClicks=0
                        uri = ComposeProvider.getImageUri(context)
                        cameraLauncher.launch(uri)
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
                var vClicks by remember{ mutableStateOf(0)}
                TextButton(onClick = {
                    vClicks ++;
                    if(vClicks==1){
                        showVideo=true;
                    }else{
                        vClicks=0
                        uri = ComposeProvider.getImageUri(context)
                        videoLauncher.launch(uri)
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
                var aClicks by remember{ mutableStateOf(0)}
                TextButton(onClick = {
                    aClicks ++;
                    if(aClicks==1){
                        showAudio=true;
                    }else{
                        aClicks=0
                        audioPickerLauncher.launch("audio/*")
                    }

                }) {
                    Row(
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Filled.Mic, contentDescription = "Audio")
                        Text(text = " Audio")
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

@TypeConverter
fun toUriList(uriListString: String?): List<Uri> {
    if (uriListString != null) {
        return uriListString.split("|").map { Uri.parse(it) }
    }
    return listOf()
}


@Preview(showBackground = true)
@Composable
fun EditNoteForm() {
    MyNotesTheme {
        Greeting4(navHostController = rememberNavController()
        )
    }
}