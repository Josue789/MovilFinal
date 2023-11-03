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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.mynotes.ViewModel.NewNoteViewModel
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
              newTareaViewModel: NewTareaViewModel = viewModel(),
              modifier: Modifier = Modifier) {


    Scaffold (
        modifier = Modifier
            .padding(5.dp),
        topBar={
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                name(title = newTareaViewModel.name,
                    updateName = {newTareaViewModel.updateName(it)},
                    modifier = Modifier)
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

            Row(modifier = modifier
                .padding(top = 5.dp)
                .fillMaxWidth()){

                startDate(
                    newTareaViewModel = newTareaViewModel ,
                    modifier = Modifier)

            }
            Row(modifier = modifier
                .padding(top = 5.dp)
                .fillMaxWidth()){

                endDate(
                    newTareaViewModel = newTareaViewModel ,
                    modifier = Modifier)

            }

            content(
                content = newTareaViewModel.content,
                updateContent =  {newTareaViewModel.updateContent(it)},
                modifier = Modifier
            )

        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun name(title: String, updateName: (String) -> Unit, modifier: Modifier){
    TextField(
        value = title,
        onValueChange =updateName,
        label = { Text(text = "Nombre tarea")},
        textStyle=MaterialTheme.typography.headlineMedium,
        modifier = modifier
            .fillMaxWidth()
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun content(content: String, updateContent: (String) -> Unit, modifier: Modifier){
    TextField(
        value = content,
        onValueChange =updateContent,
        label={ Text(text = "Descripcion")},
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun startDate(newTareaViewModel: NewTareaViewModel, modifier: Modifier){
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Obtiene el año dias y mes
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            newTareaViewModel.updateStart("$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear")
        }, year, month, dayOfMonth
    )
    TextField(
        value = if (newTareaViewModel.start.isNotEmpty()) {
            newTareaViewModel.start
        } else {
            "dd/mm/aaaa"
        },
        onValueChange = {newTareaViewModel.updateStart(it)},
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
private fun endDate(newTareaViewModel: NewTareaViewModel, modifier: Modifier){
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Obtiene el año dias y mes
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            newTareaViewModel.updateEnd("$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear")
        }, year, month, dayOfMonth
    )
    TextField(
        value = if (newTareaViewModel.end.isNotEmpty()) {
            newTareaViewModel.end
        } else {
            "dd/mm/aaaa"
        },
        onValueChange = {newTareaViewModel.updateStart(it)},
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview5() {
    MyNotesTheme {
        Greeting5(navHostController = rememberNavController())
    }
}