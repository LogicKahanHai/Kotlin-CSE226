package com.example.lpuattendanceapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("user_preferences", MODE_PRIVATE)

        // Check if the user is already registered
        val userType = sharedPreferences.getString("user_type", null)

        if (userType != null) {
            // If user type exists, navigate to respective home page
            if (userType == "student") {
                startActivity(Intent(this, StudentHomeActivity::class.java))
            } else if (userType == "teacher") {
                startActivity(Intent(this, TeacherHomeActivity::class.java))
            }
            finish()  // Close MainActivity so the user cannot return to it
        } else {

            val btnStudent = findViewById<Button>(R.id.btnStudent)
            val btnTeacher = findViewById<Button>(R.id.btnTeacher)

            btnStudent.setOnClickListener {
                startActivity(Intent(this, StudentRegistrationActivity::class.java))
            }

            btnTeacher.setOnClickListener {
                startActivity(Intent(this, TeacherRegistrationActivity::class.java))
            }
        }
    }
}
