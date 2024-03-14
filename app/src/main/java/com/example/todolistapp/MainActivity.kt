package com.example.todolistapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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

data class ToDoItem(
    var name: String,
    var quantity: String,
    var isEditing: MutableState<Boolean> = mutableStateOf(false)
)

@Composable
fun ToDoListAppUi(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        EditableItem()

    }
}


@Composable
fun EditableItem() {

    var booleanState by remember { mutableStateOf(false) }
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }

    // Important to understand
    var itemList by remember { mutableStateOf(listOf<ToDoItem>()) }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { booleanState = true }) {
            Text(
                text = "Add Item",
                fontSize = 18.sp
            )
        }

        // - Lazy column
        LazyColumn {
            items(itemList) { item ->        // Important

                if (item.isEditing.value) {
                    ToDoItemEditor(
                        item,

                        // definition
                        onEditComplete = {
                                name, quantity ->

                            item.name = name
                            item.quantity = quantity
                        },
                    )
                }else {
                    ListItem(
                        item = item,
                        onDeleteComplete = { itemList = itemList - item }
                    )
                }
            }
        }
    }

    Spacer(modifier = Modifier.padding(18.dp))
    if (booleanState) {
        Box {
            AlertDialog(
                onDismissRequest = { booleanState = false },
                confirmButton = {
                    Button(
                        onClick = {
                            // Have to understand
                            val newItem = ToDoItem(itemName, itemQuantity)
                            itemList = itemList + newItem
                            booleanState = false
                        }) {
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

@SuppressLint("UnrememberedMutableState")
@Composable
fun ListItem(
    item: ToDoItem,
    onDeleteComplete: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Row {
            Text(
                text = item.name,
                fontSize = 24.sp,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = item.quantity,
                fontSize = 24.sp,
                modifier = Modifier.padding(8.dp)
            )

            // I need to use lambda function here

            IconButton(onClick = { onDeleteComplete() }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Edit"
                )
            }

            IconButton(onClick = {
                //isEditing.value = true
                item.isEditing.value = true
            }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit"
                )
            }
        }
    }
}

@Composable
fun ToDoItemEditor(
    item: ToDoItem,

    // declaration
    onEditComplete: (String, String) -> Unit,
    ) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var editableName by remember { mutableStateOf(item.name) }
        var editableQuantity by remember { mutableStateOf(item.quantity) }


        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = editableName,
                onValueChange = { editableName = it },
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = editableQuantity,
                onValueChange = { editableQuantity = it },
                modifier = Modifier.weight(1f)
            )
        }

        Button(onClick = {

            // calling
            onEditComplete(editableName, editableQuantity)
            item.isEditing.value = false
        }) {
            Text(text = "Save")
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