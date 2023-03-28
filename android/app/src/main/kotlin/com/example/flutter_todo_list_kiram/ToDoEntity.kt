package com.example.flutter_todo_list_kiram;

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDo(
        @PrimaryKey val uid: Int,
        @ColumnInfo(name = "task") val task: String?,
        @ColumnInfo(name = "status") val status: Int?
)



