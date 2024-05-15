package com.example.todolistapp

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolistapp.ui.theme.ToDoListAppTheme

@Composable
fun ToDoListApp(
    sharedPreferences: SharedPreferences,
    viewModel: ToDoListAppViewModel,
    dateList: SnapshotStateList<String>,
) {


    var taskName = remember { mutableStateOf("") }
    val isEditing = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        var showDialog = remember { mutableStateOf(false) }
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "To do list",
                Modifier
                    .padding(8.dp)
                    .clickable { showDialog.value = true }
                    .weight(1f),
                fontSize = 24.sp
            )

            Button(onClick = { showDialog.value = true }) {
                Text(
                    text = "Add ToDo",
                    Modifier
                        .padding(8.dp),
                    fontSize = 16.sp
                )
            }
        }

        if (showDialog.value) {
            AddItem(showDialog, sharedPreferences, viewModel, dateList, taskName)
        }


        ShowToDo(viewModel, sharedPreferences, dateList, taskName, isEditing)


    }
}

@Composable
fun AddItem(
    showDialog: MutableState<Boolean>,
    sharedPreferences: SharedPreferences,
    viewModel: ToDoListAppViewModel,
    dateList: MutableList<String>,
    taskName: MutableState<String>
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                confirmButton = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(onClick = {
                            // need to handel exception
                            println("Before")
                            viewModel.addTask(sharedPreferences, taskName.value, dateList)
                            showDialog.value = false
                            println("After")
                        }) {
                            Text(text = "Add")
                        }

                        Button(onClick = { showDialog.value = false }) {
                            Text(text = "Cancel")
                        }
                    }
                },
                title = { Text(text = "Add Your Task") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = taskName.value,
                            onValueChange = { taskName.value = it },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }
                }
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun ShowToDo(
    viewModel: ToDoListAppViewModel,
    sharedPreferences: SharedPreferences,
    dateList: MutableList<String>,
    taskName: MutableState<String>,
    isEditing: MutableState<Boolean>
) {
    var taskList = mutableStateOf(viewModel.getTaskList(sharedPreferences, dateList))


    LazyColumn {
        items(taskList.value) {
            task ->

            val isEditingValue = viewModel.getIsEditingValue(sharedPreferences, task)

            if (isEditing.value && isEditingValue) {
                taskName.value = task
                EditTask(viewModel, sharedPreferences, dateList, taskName, task, isEditing)
            }else {
                if (task != null ) {
                    DisplayTask(
                        viewModel,
                        dateList,
                        isEditing,
                        sharedPreferences,
                        task
                    )
                }
            }
        }
    }
}

@Composable
fun DisplayTask(
    viewModel: ToDoListAppViewModel,
    dateList: MutableList<String>,
    isEditing: MutableState<Boolean>,
    sharedPreferences: SharedPreferences,
    task: String
) {

    var isEditingBoolean : Boolean

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(
                border = BorderStroke(2.dp, Color(0XFF018786)),
                shape = RoundedCornerShape(20)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row {

            Text(
                text = task,
                Modifier
                    .padding(8.dp)
                    .weight(1f)
            )

            Row(modifier = Modifier.padding(8.dp)) {
                IconButton(onClick = {
                    isEditingBoolean = true
                    isEditing.value = isEditingBoolean
                    viewModel.removeIsEditing(sharedPreferences, task)
                    viewModel.addIsEditing(sharedPreferences, task, isEditingBoolean)
                }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                }
                IconButton(onClick = {
                    viewModel.removeTask(dateList, sharedPreferences, task)
                }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                }
            }

        }
    }
}

@Composable
fun EditTask(
    viewModel: ToDoListAppViewModel,
    sharedPreferences: SharedPreferences,
    dateList: MutableList<String>,
    taskName: MutableState<String>,
    task: String,
    isEditing: MutableState<Boolean>
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(
                border = BorderStroke(2.dp, Color(0XFF018786)),
                shape = RoundedCornerShape(20)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {

            OutlinedTextField(
                value = taskName.value,
                onValueChange = { taskName.value = it },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .weight(1f)
            )

            Row(modifier = Modifier
                .padding(8.dp)
                .weight(1f)
            ) {
                IconButton(onClick = {
                    viewModel.removeTask(dateList, sharedPreferences, task)

                    isEditing.value = false
                    viewModel.removeIsEditing(sharedPreferences, task)
                    viewModel.addIsEditing(sharedPreferences, task, isEditing.value)

                    viewModel.addTask(sharedPreferences, taskName.value, dateList)

                }) {
                    Icon(imageVector = Icons.Default.Done, contentDescription = null)
                }
                IconButton(onClick = {
                    isEditing.value = false
                    viewModel.removeIsEditing(sharedPreferences, task)
                    viewModel.addIsEditing(sharedPreferences, task, isEditing.value)
                }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                }
            }

        }
    }
}