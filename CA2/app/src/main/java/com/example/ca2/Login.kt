package com.example.ca2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        databaseHelper = DatabaseHelper(this)

        val mobileEditText = findViewById<EditText>(R.id.etMobileLogin)
        val passwordEditText = findViewById<EditText>(R.id.etPasswordLogin)
        val loginButton = findViewById<Button>(R.id.btnLogin)

        loginButton.setOnClickListener {
            val mobile = mobileEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (mobile.isNotEmpty() && password.isNotEmpty()) {
                if (databaseHelper.checkUser(mobile, password)) {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    // Navigate to Available Rooms Screen
                } else {
                    Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
