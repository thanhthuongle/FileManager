package com.example.filemanager

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.filemanager.databinding.ActivityMainBinding
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.reflect.typeOf

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val data = mutableListOf<DirectoryModel>()
    private val directoryAdapter = DirectoryAdapter(data)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.main)

        binding.studentList.adapter = directoryAdapter

        binding.studentList.setOnItemClickListener() { directoryAdapter, view, i, l ->
            if (data[i].directoryType == "folder") {
//              Toast.makeText(this, "Check folder $i", Toast.LENGTH_SHORT).show()
                val path = data[i].directoryPath
                val name = data[i].directoryName
                val intent = Intent(this, BrowseActivity::class.java)
                intent.putExtra("path", path)
                intent.putExtra("name", name)
                startActivity(intent)
            }
            else if(data[i].directoryType == "file") {
//                Toast.makeText(this, "Check file $i", Toast.LENGTH_SHORT).show()
                val file = File(data[i].directoryPath)
                if (isTextFile(file)) {
                    val path = data[i].directoryPath
                    val name = data[i].directoryName
                    val intent = Intent(this, ContentFileActivity::class.java)
                    intent.putExtra("path", path)
                    intent.putExtra("name", name)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Loại file không được hỗ trợ", Toast.LENGTH_LONG).show()
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                startActivity(intent)
            }
        }

        val root = Environment.getExternalStorageDirectory()
        Log.v("root", "${root}")
        for(entry in root.listFiles()!!) {
            if (entry.isDirectory) {
                Log.v("root", "${entry.path} - Type : ${entry::class.simpleName}")
                data.add(DirectoryModel(entry.name, "folder", entry.path))
                directoryAdapter.notifyDataSetChanged()
            }
            else if (entry.isFile) {
                Log.v("root", "${entry.path} - Type : ${entry::class.simpleName}")
                data.add(DirectoryModel(entry.name, "file", entry.path))
                directoryAdapter.notifyDataSetChanged()
            }
            else Log.v("root", "else: ${entry.path}")
        }
    }
    fun isTextFile(file: File): Boolean {
        val fileName = file.name.lowercase()
        return fileName.endsWith(".txt") || fileName.endsWith(".csv") ||
                fileName.endsWith(".html") || fileName.endsWith(".json") ||
                fileName.endsWith(".xml")
    }
}