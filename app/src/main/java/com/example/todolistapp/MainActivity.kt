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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.mutableIntStateOf
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

    var booleanState by remember { mutableStateOf(false) }

    Column {
        Button(onClick = { booleanState = true }) {
            Text(
                text = "Add Item",
                fontSize = 18.sp
            )
        }

        // - Lazy column

        val itemNameList = listOf(
            "Mouse" to 1,
            "Keyboard" to 2,
            "Tap" to 5
        )
        val itemQuantityList = listOf(1, 2, 5)

        // // need to understand well
        LazyColumn {
            items(itemNameList) { (item, quantity) ->
                ListItem(name = item, quantity = quantity.toString())
            }
        }

        // need to understand well
        LazyColumn {
            items(itemQuantityList) { item ->
                ListItem(name = "", quantity = "")
            }
        }
    }


    Spacer(modifier = Modifier.padding(18.dp))

    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }
    if (booleanState) {
        Box {
            AlertDialog(
                onDismissRequest = { booleanState = false },
                confirmButton = {
                    Button(
                        onClick = { /*TODO*/ }) {
                        Text(text = "Save")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { booleanState = false }) {
                        Text(text = "Cancel")
                    }
                },
                title = { Text("To-Do List Item") },
                text = { Column {
                        TextField(
                            value = itemName,
                            onValueChange = { itemName = it },
                            label = {
                                Text(
                                    text = "Enter item name",
                                    fontSize = 18.sp
                                )
                            },
                        )


                        TextField(
                            value = itemQuantity,
                            onValueChange = { itemQuantity = it },
                            label = {
                                Text(
                                    text = "Enter quantity",
                                    fontSize = 18.sp
                                )
                            },
                        )
                    }
                }
            )
        }
    }
}


@Composable
fun ListItem(name: String, quantity: String) {
    Row {
        Text(
            text = name,
            fontSize = 24.sp,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = quantity,
            fontSize = 24.sp,
            modifier = Modifier.padding(8.dp)
        )
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