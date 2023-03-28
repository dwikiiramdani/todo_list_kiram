package com.example.flutter_todo_list_kiram;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todo")
    fun getAll(): List<ToDo>

    @Query("SELECT * FROM user WHERE uid IN (:todoIds)")
    fun loadAllByIds(userIds: IntArray): List<ToDo>

    @Query("SELECT * FROM user WHERE task LIKE :first AND " +
            "status LIKE :last LIMIT 1")
    fun findByName(first: String, last: Int): ToDo

    @Insert
    fun insertAll(vararg todos: ToDo)

    @Delete
    fun delete(user: ToDo)
}
