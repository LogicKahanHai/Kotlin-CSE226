package com.example.lpuattendanceapp

import android.database.Cursor
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.google.zxing.BarcodeFormat

class StudentHomeActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_home)

        dbHelper = DatabaseHelper(this)

        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        val ivQRCode = findViewById<ImageView>(R.id.ivQRCode)

        // Fetch student data
        val cursor: Cursor = dbHelper.readableDatabase.query(
            DatabaseHelper.TABLE_STUDENT,
            null,
            null,
            null,
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.STUDENT_NAME))
            val regNo = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.STUDENT_REG_NO))
            val section = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.STUDENT_SECTION))
            val rollNo = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.STUDENT_ROLL_NO))

            // Display welcome message with student's name
            tvWelcome.text = "Welcome, $name!"

            // QR Code Data
            val qrData = "Name: $name\nRegNo: $regNo\nSection: $section\nRollNo: $rollNo"

            // Generate QR Code
            try {
                val barcodeEncoder = BarcodeEncoder()
                val bitmap = barcodeEncoder.encodeBitmap(qrData, BarcodeFormat.QR_CODE, 400, 400)
                ivQRCode.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        cursor.close()
    }
}
