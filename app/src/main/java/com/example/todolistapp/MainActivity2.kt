package com.example.todolistapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
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

    LazyColumn {
        items(itemsList) {item ->
            ListItem(text = item.toString())
        }
    }
}

@Composable
fun ListItem(text: String) {

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