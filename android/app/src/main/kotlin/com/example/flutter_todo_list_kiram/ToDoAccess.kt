package com.example.flutter_todo_list_kiram;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todo")
    fun getAll(): MutableList<ToDo>

    @Query("SELECT * FROM todo WHERE uid IN (:todoIds)")
    fun loadAllByIds(todoIds: IntArray): MutableList<ToDo>

    @Query("SELECT * FROM todo WHERE task LIKE :first AND " +
            "status LIKE :last LIMIT 1")
    fun findByName(first: String, last: Int): ToDo

    @Insert
    fun insertAll(vararg todos: ToDo)

    @Delete
    fun delete(toDo: ToDo)
}
