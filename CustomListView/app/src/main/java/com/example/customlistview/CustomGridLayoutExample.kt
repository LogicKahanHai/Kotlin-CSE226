package com.example.customlistview

import android.os.Bundle
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CustomGridLayoutExample : AppCompatActivity() {
    lateinit var courseGV: GridView
    lateinit var courseList: List<GridViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_custom_grid_layout_example)

        courseGV = findViewById(R.id.GRV)
        courseList = ArrayList<GridViewModel>()

        courseList = courseList + GridViewModel("C++", R.drawable.cplusplus)
        courseList = courseList + GridViewModel("Java", R.drawable.java)
        courseList = courseList + GridViewModel("Kotlin", R.drawable.kotlin)
        courseList = courseList + GridViewModel("Python", R.drawable.python)
        courseList = courseList + GridViewModel("Android Studio", R.drawable.androidstudio)
        courseList = courseList + GridViewModel("React Native", R.drawable.ic_launcher_background)
        courseList = courseList + GridViewModel("Flutter", R.drawable.ic_launcher_foreground)

        val courseAdapter = GridViewAdapter(courseList, this)
        courseGV.adapter = courseAdapter

        courseGV.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            Toast.makeText(applicationContext, courseList[position].courseName + " selected", Toast.LENGTH_SHORT).show()
        }


    }
}