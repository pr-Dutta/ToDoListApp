package com.example.todolistapp

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Date


class ToDoListAppViewModel() : ViewModel() {

    fun addTask(
        sharedPref: SharedPreferences,
        taskName: String,
        dateList: MutableList<String>,
        isEditing: Boolean = false
    ) {

        println("Before addTask")
        val date = getCurrentDateAndTime()
        dateList.add(date)


        val editor = sharedPref.edit()
        editor.apply {
            editor.putString(date, taskName)
            editor.putBoolean(taskName, isEditing)
            editor.putStringSet("dateList", dateList.toMutableSet())
            editor.apply()
        }
        println("After addTask")
    }

    fun getDateList(sharedPref: SharedPreferences) : List<String>? {
        return  sharedPref.getStringSet("dateList", null)?.toList()
    }

    fun addIsEditing(
        sharedPref: SharedPreferences,
        taskName: String,
        isEditing: Boolean
    ) {

        val editor = sharedPref.edit()
        editor.apply {
            editor.putBoolean(taskName, isEditing)
            editor.apply()
        }
    }

    fun removeTask(
        dateList: MutableList<String>,
        sharedPref: SharedPreferences,
        task: String
    ) {

        val key = getKeyToRemoveAndEditTask(dateList, sharedPref, task)

        val editor = sharedPref.edit()
        editor.apply {
            editor.remove(key)
            editor.apply()
        }
        dateList.remove(key)
    }

    fun removeIsEditing(
        sharedPref: SharedPreferences,
        key: String
    ) {

        val editor = sharedPref.edit()
        editor.apply {
            editor.remove(key)
            editor.apply()
        }
    }

    fun getTaskList(sharedPref: SharedPreferences, dateList: MutableList<String>): List<String> {

        val list = mutableListOf<String>()

        dateList.forEach {
            val task = sharedPref.getString(it, null)
            list.add(task.toString())
        }
        return list.toList()
    }


    fun getCurrentDateAndTime() : String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val currentDate = sdf.format(Date())
        return currentDate
    }

    fun getKeyToRemoveAndEditTask(
        dateList: MutableList<String>,
        sharedPreferences: SharedPreferences,
        task: String
    ): String? {

        var key: String? = null

        // need to check this
        dateList.forEach {
            val taskName = sharedPreferences.getString(it, null)
            if (taskName == task) {
                key = it
            }
        }
        return key
    }

    fun getIsEditingValue(
        sharedPref: SharedPreferences,
        task: String
    ) : Boolean {
        val key = sharedPref.getBoolean(task, false)
        return key
    }
}