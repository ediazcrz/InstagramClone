package com.example.instagramclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var etDescription: EditText
    private lateinit var photoFile: File
    private lateinit var ivPostImage: ImageView
    private val photoFileName: String = "photo.jpg"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}