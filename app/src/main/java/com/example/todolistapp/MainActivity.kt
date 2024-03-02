package com.example.todolistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.sp
import com.example.todolistapp.ui.theme.ToDoListAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoListAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ToDoListAppUi()
                }
            }
        }
    }
}

@Composable
fun ToDoListAppUi(modifier: Modifier = Modifier) {
    Column(
        Modifier.fillMaxSize(),
        //verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        EditableItem()

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditableItem() {

    var booleanState by remember { mutableStateOf(true) }
    Button(onClick = { booleanState = true }) {
        Text(
            text = "Add Item",
            fontSize = 18.sp
        )
    }

    Spacer(modifier = Modifier.padding(18.dp))

    var itemName by remember { mutableStateOf("") }
    if (booleanState) {
        Box {
            AlertDialog(
                onDismissRequest = { booleanState = false },
                confirmButton = { Text("Save") },
                //dismissButton = { Text("Cancel") },
                title = { Text("To-Do List Item") },
                text = { TextField(
                    value = itemName,
                    onValueChange = { itemName = it },
                    label = {
                        Text(
                            text = "Enter item name",
                            fontSize = 18.sp
                        )
                    },
                ) }
            )
        }
    }
}

@Preview(
    showBackground = true,
    name = "TO-DO List App",
    showSystemUi = true
)
@Composable
fun GreetingPreview() {
    ToDoListAppTheme {
        ToDoListAppUi()
    }
}