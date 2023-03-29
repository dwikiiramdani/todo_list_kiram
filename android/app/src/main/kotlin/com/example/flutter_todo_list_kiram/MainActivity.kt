package com.example.flutter_todo_list_kiram

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.room.Room
import androidx.room.RoomDatabase
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import kotlinx.coroutines.*
import java.io.File

class MainActivity : FlutterActivity() {
    private val CHANNEL = "flutter_todo_list_kiram/platform"

    var db: ToDoDB? = null

//    private val db by lazy { ToDoDB(this) }

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            CHANNEL
        ).setMethodCallHandler { call, result ->
            if (call.method == "init_database") {
                initDatabase()
                getToDo()
                result.success(dataHash)
            }
            if (call.method == "check_database") {
                var isExist = doesDatabaseExist(context)
                result.success(isExist)
            }
            if (call.method == "insert_database") {
                val task = call.argument<String>("task")
                addToDo(task)
                initDatabase()
                getToDo()
                result.success(dataHash)
            }
            if (call.method == "update_database") {
                val id = call.argument<Int>("id")
                val task = call.argument<String>("task")
                completeToDo(id, task)
                initDatabase()
                getToDo()
                result.success(dataHash)
            } else {
                result.notImplemented()
            }
            // This method is invoked on the main thread.
            // TODO
        }
    }

    private fun initDatabase() {
        db = Room.databaseBuilder(
            applicationContext,
            ToDoDB::class.java, "todo-db"
        ).allowMainThreadQueries().build()
    }

    private fun doesDatabaseExist(context: Context): Boolean {
        val dbFile: File = context.getDatabasePath("todo-db")
        return dbFile.exists()
    }

    private var dataHash: MutableList<MutableMap<String, String>> =
        mutableListOf<MutableMap<String, String>>()
    private fun setDataTodo(data: MutableList<MutableMap<String, String>>) {
        dataHash = data
    }

    private fun getToDo(){
        var todos: MutableList<ToDo>? = null
        val res: MutableList<MutableMap<String, String>> =
            mutableListOf<MutableMap<String, String>>()
        todos = db?.todoDao()?.getTodo()
        if (todos != null) {
            for (item in todos!!) {
                val itemInHash: MutableMap<String, String> = HashMap()
                itemInHash["id"] = item.uid.toString()
                itemInHash["task"] = item.task.toString()
                itemInHash["status"] = item.status.toString()
                res.add(itemInHash)
            }
            setDataTodo(res)
        }
        setDataTodo(res)
    }

    private fun addToDo(task: String?) {
        db?.todoDao()?.addTodo(
            ToDo(0, task, 0)
        )
    }

    private fun completeToDo(id: Int?, task: String?) {
        id?.let { ToDo(it, task, 1) }?.let {
            db?.todoDao()?.updateTodo(
                it
            )
        }
    }

}
