package com.example.lpuattendanceapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly

class StudentRegistrationActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_registration)

        dbHelper = DatabaseHelper(this)
        sharedPreferences = getSharedPreferences("user_preferences", MODE_PRIVATE)

        // Initialize the input fields and button
        val etName = findViewById<EditText>(R.id.etStudentName)
        val etRegNo = findViewById<EditText>(R.id.etRegNo)
        val etSection = findViewById<EditText>(R.id.etSection)
        val etRollNo = findViewById<EditText>(R.id.etRollNo)
        val btnRegister = findViewById<Button>(R.id.btnRegisterStudent)

        btnRegister.setOnClickListener {
            val name = etName.text.toString()
            val regNo = etRegNo.text.toString()
            val section = etSection.text.toString()
            val rollNo = etRollNo.text.toString()

            // Check if any field is empty
            if (name.isEmpty() || regNo.isEmpty() || section.isEmpty() || rollNo.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else if(!regNo.isDigitsOnly() || regNo.length < 8) {
                Toast.makeText(this, "Please enter valid registration number", Toast.LENGTH_SHORT).show()
            } else {
                // Optional: clear the student table (caution: this will delete all students in the table)
                dbHelper.clearStudentTable()

                // Insert student data into database
                val result = dbHelper.insertStudent(name, regNo, section, rollNo)

                if (result != -1L) {
                    sharedPreferences.edit().putString("user_type", "student").apply()
                    // Registration successful, navigate to StudentHomeActivity
                    Toast.makeText(this, "Student Registered Successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, StudentHomeActivity::class.java))
                    finish() // Close the registration screen
                } else {
                    // Registration failed
                    Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
