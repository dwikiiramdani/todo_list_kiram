package com.example.flutter_todo_list_kiram;

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDo(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    val task: String?,
    val status: Int?
)



