package com.example.lpuattendanceapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TeacherRegistrationActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_registration)

        dbHelper = DatabaseHelper(this)
        sharedPreferences = getSharedPreferences("user_preferences", MODE_PRIVATE)

        val etName = findViewById<EditText>(R.id.etTeacherName)
        val etUID = findViewById<EditText>(R.id.etUID)
        val etSections = findViewById<EditText>(R.id.etSections)
        val btnRegister = findViewById<Button>(R.id.btnRegisterTeacher)

        btnRegister.setOnClickListener {
            val name = etName.text.toString()
            val uid = etUID.text.toString()
            val sections = etSections.text.toString()

            if (name.isEmpty() || uid.isEmpty() || sections.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                dbHelper.clearTeacherTable()
                val result = dbHelper.insertTeacher(name, uid, sections)
                if (result != -1L) {
                    sharedPreferences.edit().putString("user_type", "teacher").apply()
                    Toast.makeText(this, "Teacher Registered Successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, TeacherHomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
