package com.example.mynotes

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings.Global.getString
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mynotes.ViewModel.NewTareaViewModel
import com.example.mynotes.ui.theme.MyNotesTheme
import java.util.Calendar
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mynotes.Multimedia.ComposeProvider
import com.example.mynotes.Multimedia.DialogShowAudioSelected
import com.example.mynotes.Multimedia.DialogShowFileSelected
import com.example.mynotes.Multimedia.DialogShowImageTake
import com.example.mynotes.Multimedia.DialogShowVideoTake
import com.example.mynotes.Navigation.Screens
import com.example.mynotes.Notifications.createChannelNotification
import com.example.mynotes.Notifications.workAlarm
import com.example.mynotes.ViewModel.DoesDetails
import com.example.mynotes.ViewModel.NewNoteViewModel
import kotlinx.coroutines.launch
import java.util.Date

class NewTarea : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyNotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting5(navHostController = rememberNavController())
                }
            }
        }
    }
}




@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting5(navHostController: NavHostController,
              newTareaViewModel: NewTareaViewModel = viewModel(factory = AppViewModelProvider.Factory),
              modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    val idCanal = "CanalTareas"
    val scope = rememberCoroutineScope()
    val item = newTareaViewModel.doesUiState.doesDetails
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



    //Crea el canal de notificaciones
    LaunchedEffect(Unit){
        createChannelNotification(idCanal,context)
    }

    Scaffold (
        modifier = Modifier,
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
                            val all = item.end.split(",")
                            all.map {
                                if(it.isNotEmpty()){
                                    val strs = it.split(":")
                                    workAlarm(
                                        context = context,
                                        title = item.titulo,
                                        longDesc = item.contenido,
                                        fchEnd = item.start,
                                        hora = strs[0].toInt(),
                                        minutos = strs[1].toInt()
                                    )
                                }

                            }
                            coroutineScope.launch {
                                newTareaViewModel.saveItem()
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
            FloatingActionButton(onClick = {showBottomSheet = true}) {
                Icon(Icons.Default.AttachFile, contentDescription = "Add")
            }
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

            name(
                title = newTareaViewModel.doesUiState.doesDetails,
                updateName = newTareaViewModel::updateUiState,
                modifier = modifier)

            Row(modifier = modifier
                .fillMaxWidth()){
                startDate(
                    start = newTareaViewModel.doesUiState.doesDetails,
                    updateStart = newTareaViewModel::updateUiState,
                    newTareaViewModel = newTareaViewModel ,
                    modifier = Modifier)

            }
            Row(modifier = modifier
                .fillMaxWidth()){
                endDate(
                    end = newTareaViewModel.doesUiState.doesDetails,
                    updateEnd = newTareaViewModel::updateUiState,
                    newTareaViewModel = newTareaViewModel ,
                    modifier = Modifier)

            }
            content(
                content = newTareaViewModel.doesUiState.doesDetails,
                updateContent =  newTareaViewModel::updateUiState,
                modifier = Modifier
            )

        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun name(
    title: DoesDetails,
    updateName: (DoesDetails) -> Unit,
    modifier: Modifier){
    TextField(
        value = title.titulo,
        onValueChange = { updateName(title.copy(titulo = it)) },
        label = { Text(text = "Nombre tarea")},
        textStyle=MaterialTheme.typography.headlineMedium,
        modifier = modifier
            .fillMaxWidth()
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun content(
    content: DoesDetails,
    updateContent: (DoesDetails) -> Unit,
    modifier: Modifier){
    TextField(
        value = content.contenido,
        onValueChange = {updateContent(content.copy(contenido = it))},
        label={ Text(text = "Descripcion")},
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    )
}
/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun name(
    title: NewTareaViewModel.DoesDetails,
    updateName: (NewTareaViewModel.DoesDetails) -> Unit,
    modifier: Modifier){
    TextField(
        value = title.titulo,
        onValueChange = { updateName(title.copy(titulo = it)) },
        label = { Text(text = "Nombre tarea")},
        textStyle=MaterialTheme.typography.headlineMedium,
        modifier = modifier
            .fillMaxWidth()
    )
}
*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun startDate(
    start: DoesDetails ,
    updateStart: (DoesDetails) -> Unit,
    newTareaViewModel: NewTareaViewModel,
    modifier: Modifier){
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Obtiene el año dias y mes
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            //newTareaViewModel.updateStart("$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear")
            updateStart(start.copy(start="$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"))
        }, year, month, dayOfMonth
    )
    TextField(
        value = if (start.start.isNotEmpty()) {
            start.start
        } else {
            "dd/mm/aaaa"
        },
        onValueChange = {updateStart(start.copy(start=it))},
        readOnly = true,
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            IconButton(onClick = {
                datePicker.show()
            }
            ) {
                Icon(Icons.Filled.DateRange, contentDescription = "")
            }
        }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun endDate(
    newTareaViewModel: NewTareaViewModel,
    end: DoesDetails ,
    updateEnd: (DoesDetails) -> Unit,
    modifier: Modifier){
    var lineaNotificaciones = end.end


    //Obtiene la hora y contexto actual
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val hours = calendar[Calendar.HOUR_OF_DAY]
    val minutes = calendar[Calendar.MINUTE]

    //Listener
    val listener = TimePickerDialog.OnTimeSetListener{
            view,hora,minutos ->
        val text = "$hora:$minutos"
        lineaNotificaciones += "$hora:$minutos,"
        updateEnd(end.copy(end=lineaNotificaciones))
    }

    val timePicker = TimePickerDialog(
        context,
        listener, hours, minutes,true
    )
    TextField(
        value = if (end.end.isNotEmpty()) {
            end.end
        } else {
            "00:00"
        },
        onValueChange = {updateEnd(end.copy(end=lineaNotificaciones))},
        readOnly = true,
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            IconButton(onClick = {
                timePicker.show()
            }
            ) {
                Icon(Icons.Filled.Notifications, contentDescription = "")
            }
        }

    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview5() {
    MyNotesTheme {
        Greeting5(navHostController = rememberNavController())
    }
}