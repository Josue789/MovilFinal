package com.example.mynotes

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mynotes.ViewModel.NewTareaViewModel
import com.example.mynotes.ui.theme.MyNotesTheme
import java.util.Calendar
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mynotes.Navigation.Screens
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting5(navHostController: NavHostController,
              newTareaViewModel: NewTareaViewModel = viewModel(factory = AppViewModelProvider.Factory),
              modifier: Modifier = Modifier) {

    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
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

                TextButton(onClick = { /*TODO*/ }) {
                    Row(
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Filled.CameraAlt, contentDescription = "Photo")
                        Text(text = "Foto/Imagen")
                    }
                }
                TextButton(onClick = { /*TODO*/ }) {
                    Row(
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Filled.Mic, contentDescription = "Photo")
                        Text(text = "Audio")
                    }
                }

                TextButton(onClick = { /*TODO*/ }) {
                    Row(
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Filled.FileOpen, contentDescription = "Photo")
                        Text(text = "Documento")
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun content(
    content: NewTareaViewModel.DoesDetails,
    updateContent: (NewTareaViewModel.DoesDetails) -> Unit,
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
    start: NewTareaViewModel.DoesDetails ,
    updateStart: (NewTareaViewModel.DoesDetails) -> Unit,
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
    end: NewTareaViewModel.DoesDetails ,
    updateEnd: (NewTareaViewModel.DoesDetails) -> Unit,
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
            //newTareaViewModel.updateEnd("$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear")
            updateEnd(end.copy(end="$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"))
        }, year, month, dayOfMonth
    )
    TextField(
        value = if (end.end.isNotEmpty()) {
            end.end
        } else {
            "dd/mm/aaaa"
        },
        onValueChange = {updateEnd(end.copy(end=it))},
        readOnly = true,
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            IconButton(onClick = {
                datePicker.show()
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