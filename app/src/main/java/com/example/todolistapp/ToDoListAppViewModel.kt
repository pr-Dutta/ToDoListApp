package com.example.todolistapp

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Date


class ToDoListAppViewModel() : ViewModel() {

    fun addTask(
        sharedPref: SharedPreferences,
        taskName: String,
        dateList: MutableList<String>
    ) {

        val date = getCurrentDateAndTime()
        dateList.add(date)

        val editor = sharedPref.edit()
        editor.apply {
            editor.putString(date, taskName)
            editor.apply()
        }
    }

    fun removeTask(
        dateList: MutableList<String>,
        sharedPref: SharedPreferences,
        task: String
    ) : String {

        val key = getKeyToRemoveTask(dateList, sharedPref, task)

        val taskName = getTask(sharedPref, key)
        print("$key -- $ $taskName")
        print("-------------------------------- haha")

        // remove nahe ho raha ha
        val editor = sharedPref.edit()
        editor.apply {
            editor.remove(key)
            editor.apply()
            println("remove done")
        }
        dateList.remove(key)
        return "Done"
    }

    fun getTaskList(sharedPref: SharedPreferences, dateList: MutableList<String>): MutableList<String> {

        val list = mutableListOf<String>()

        dateList.forEach {
            val task = sharedPref.getString(it, null)
            list.add(task.toString())
        }
        return list
    }


    fun getCurrentDateAndTime() : String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val currentDate = sdf.format(Date())
        return currentDate
    }

    fun getKeyToRemoveTask(
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

    fun getTask(sharedPref: SharedPreferences, key: String?) : String? {
        val task = sharedPref.getString(key.toString(), null)
        return task
    }
}