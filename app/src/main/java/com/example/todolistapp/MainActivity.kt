package com.example.todolistapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolistapp.ui.theme.ToDoListAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val context = LocalContext.current

            val sharedPref =  remember { context.getSharedPreferences("toDo", Context.MODE_PRIVATE) }
            val viewModel : ToDoListAppViewModel = viewModel()

            var dateList = remember { mutableStateListOf<String>() }
            viewModel.getDateList(sharedPref)?.let { dateList.addAll(it) }

            ToDoListAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ToDoListApp(sharedPref, viewModel, dateList)
                }
            }
        }
    }
}