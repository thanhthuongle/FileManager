package com.example.filemanager

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.filemanager.databinding.ActivityContentFileBinding
import com.example.filemanager.databinding.ActivityMainBinding
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader

class ContentFileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContentFileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentFileBinding.inflate(layoutInflater)
        setContentView(binding.main)

        val path = intent.getStringExtra("path")
        val name = intent.getStringExtra("name")

        binding.textView3.text = "File $name"

        if(path != null){
            val file = File(path)
            val inputStream: InputStream = file.inputStream()
            val reader: InputStreamReader = inputStream.reader()
            val content: String = reader.readText()
            reader.close()
            binding.contentFile.text = content
        } else {
            Toast.makeText(this, "Không có đường dẫn!", Toast.LENGTH_SHORT).show()
        }




    }
}