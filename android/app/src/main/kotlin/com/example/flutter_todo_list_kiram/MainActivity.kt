package com.example.flutter_todo_list_kiram

import android.content.Context
import androidx.annotation.NonNull
import androidx.room.Room
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import java.io.File

class MainActivity: FlutterActivity() {
    private val CHANNEL = "flutter_todo_list_kiram/platform"

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
                call, result ->
            // This method is invoked on the main thread.
            // TODO
        }
    }

    private fun initDatabase() {
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "todo-db"
        ).build()
    }

    private fun doesDatabaseExist(context: Context): Boolean {
        val dbFile: File = context.getDatabasePath("todo-db")
        return dbFile.exists()
    }


}
