package com.example.todolistapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolistapp.ui.theme.ToDoListAppTheme

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
    }
}

@Composable
fun PracticeUi() {

    val itemsList = listOf("Item 1", "Item 2", "Item 3", "Item 4")


    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(itemsList) { item ->
            ListItem1(text = item)
        }
    }
}

@Composable
fun ListItem1(text: String) {
    Text(
        text = text,
        fontSize = 24.sp,
        modifier = Modifier.padding(8.dp)
    )
}

@Preview(
    showBackground = true,
    name = "TO-DO List App",
    showSystemUi = true
)
@Composable
fun GreetingPreview1() {
    ToDoListAppTheme {
        PracticeUi()
    }
}