package com.example.todolistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
                    ToDoListAppUi("Android")
                }
            }
        }
    }
}

@Composable
fun ToDoListAppUi(name: String, modifier: Modifier = Modifier) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { /*TODO*/ }) {
            Text(
                text = "Add Items",
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.padding(18.dp))

        var text by remember { mutableStateOf(" ") }
        TextField(
            value = text,
            onValueChange = { newText -> text = newText },
            label = { Text(
                text = "Enter text here",
                fontSize = 18.sp) },
        )
    }
}

@Composable
fun EditableItem() {
    var text by remember { mutableStateOf(" ") }
    TextField(
        value = text,
        onValueChange = { newText -> text = newText }
    )
}

@Preview(
    showBackground = true,
    name = "TO-DO List App",
    showSystemUi = true
)
@Composable
fun GreetingPreview() {
    ToDoListAppTheme {
        ToDoListAppUi("Android")
    }
}