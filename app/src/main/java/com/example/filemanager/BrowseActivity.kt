package com.example.filemanager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.filemanager.databinding.ActivityBrowseBinding
import java.io.File

class BrowseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBrowseBinding
    private val data = mutableListOf<DirectoryModel>()
    private val directoryAdapter = DirectoryAdapter(data)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBrowseBinding.inflate(layoutInflater)
        setContentView(binding.main)

        binding.listView.adapter = directoryAdapter

        val path = intent.getStringExtra("path")
        val name = intent.getStringExtra("name")
        Log.v("root", "$path")

        binding.textView2.text = "Thư mục $name"
        if (path != null) {
            browser(path)
        } else {
            Toast.makeText(this, "Không có đường dẫn!", Toast.LENGTH_SHORT).show()
        }

        binding.listView.setOnItemClickListener { parent, view, position, id ->
            if (data[position].directoryType == "folder") {
//              Toast.makeText(this, "Check folder $i", Toast.LENGTH_SHORT).show()
                val path = data[position].directoryPath
                val name = data[position].directoryName
                val intent = Intent(this, BrowseActivity::class.java)
                intent.putExtra("path", path)
                intent.putExtra("name", name)
                startActivity(intent)
            }
            else if(data[position].directoryType == "file") {
//                Toast.makeText(this, "Check file $i", Toast.LENGTH_SHORT).show()
                val file = File(data[position].directoryPath)
                if (isTextFile(file)) {
                    val path = data[position].directoryPath
                    val name = data[position].directoryName
                    val intent = Intent(this, ContentFileActivity::class.java)
                    intent.putExtra("path", path)
                    intent.putExtra("name", name)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Loại file không được hỗ trợ", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun browser(path: String){
        val root = File(path)
        val files = root.listFiles()
        if (files == null) {
            Toast.makeText(this, "Không thể truy cập thư mục!", Toast.LENGTH_SHORT).show()
            return
        }

        data.clear()
        for(entry in files) {
            if (entry.isDirectory) {
                Log.v("root", "${entry.path} - Type : ${entry::class.simpleName}")
                data.add(DirectoryModel(entry.name, "folder", entry.path))
            }
            else if (entry.isFile) {
                Log.v("root", "${entry.path} - Type : ${entry::class.simpleName}")
                data.add(DirectoryModel(entry.name, "file", entry.path))
            }
            else Log.v("root", "else: ${entry.path}")
        }
        directoryAdapter.notifyDataSetChanged()
    }
    fun isTextFile(file: File): Boolean {
        val fileName = file.name.lowercase()
        return fileName.endsWith(".txt") || fileName.endsWith(".csv") ||
                fileName.endsWith(".html") || fileName.endsWith(".json") ||
                fileName.endsWith(".xml")
    }
}