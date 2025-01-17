package com.example.ca2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class BookingActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        databaseHelper = DatabaseHelper(this)

        val nameEditText = findViewById<EditText>(R.id.etName)
        val mobileEditText = findViewById<EditText>(R.id.etMobile)
        val dobEditText = findViewById<EditText>(R.id.etDOB)
        val passwordEditText = findViewById<EditText>(R.id.etPassword)
        val bookRoomButton = findViewById<Button>(R.id.btnBookRoom)

        bookRoomButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val mobile = mobileEditText.text.toString()
            val dob = dobEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (name.isNotEmpty() && mobile.isNotEmpty() && dob.isNotEmpty() && password.isNotEmpty()) {
                val id = databaseHelper.addUser(name, mobile, dob, password)
                if (id > 0) {
                    Toast.makeText(this, "Room Booked Successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error booking room", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
