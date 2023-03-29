package com.example.flutter_todo_list_kiram;

import androidx.room.*

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todo")
    fun getTodo(): MutableList<ToDo>

    @Insert
    fun addTodo(toDo: ToDo)

    @Update
    fun updateTodo(toDo: ToDo)

    @Delete
    fun delete(toDo: ToDo)
}
