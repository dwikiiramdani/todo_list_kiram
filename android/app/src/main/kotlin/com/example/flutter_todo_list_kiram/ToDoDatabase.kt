package com.example.flutter_todo_list_kiram

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ToDo::class], version = 1)
abstract class ToDoDB : RoomDatabase() {
    abstract fun todoDao(): ToDoDao
}