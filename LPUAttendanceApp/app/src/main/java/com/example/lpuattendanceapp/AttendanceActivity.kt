package com.example.lpuattendanceapp

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.zxing.integration.android.IntentIntegrator
import com.opencsv.CSVWriter
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

class AttendanceActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: StudentAdapter
    private val studentList = mutableListOf<Student>()

    // Function to ask for confirmation before generating the CSV
    private fun confirmAndGenerateCSV() {
        AlertDialog.Builder(this)
            .setTitle("Generate CSV")
            .setMessage("Are you sure you want to generate the CSV file?")
            .setPositiveButton("Yes") { _, _ ->
                generateCSV()
                finish() // Close Attendance Activity after generating CSV
            }
            .setNegativeButton("No", null)
            .show()
    }

    // Function to generate the CSV file
    private fun generateCSV() {
        val dateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        val currentDate = dateFormat.format(Date())

        // File path setup
        val directory = File(getExternalFilesDir(null), "Attendance")
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val fileName = "Attendance_${intent.getStringExtra("SECTION")}_${currentDate}_${intent.getStringExtra("TIME_SLOT")}.csv"
        val file = File(directory, fileName)

        try {
            val writer = CSVWriter(FileWriter(file))

            // Adding headers
            val header = arrayOf("Name", "Roll Number", "Registration Number", "Section", "Time Slot")
            writer.writeNext(header)

            // Adding student data to the CSV file
            studentList.forEach { student ->
                val row = arrayOf(
                    student.name,
                    student.rollNumber,
                    student.registrationNumber,
                    intent.getStringExtra("SECTION") ?: "",
                    intent.getStringExtra("TIME_SLOT") ?: ""
                )
                writer.writeNext(row)
            }

            writer.close()
            Toast.makeText(this, "CSV generated: ${file.absolutePath}", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error generating CSV", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance)

        dbHelper = DatabaseHelper(this)

        val section = intent.getStringExtra("SECTION")
        val timeSlot = intent.getStringExtra("TIME_SLOT")
        val tvHeader = findViewById<TextView>(R.id.tvHeader)
        val rvStudents = findViewById<RecyclerView>(R.id.rvStudents)
        val btnScanQrCode = findViewById<Button>(R.id.btnScanQrCode)
        val btnGenerateCsv = findViewById<Button>(R.id.btnGenerateCsv)

        // Set header text dynamically
        tvHeader.text = "Attendance for Section: $section, Time Slot: $timeSlot"

        // Setup RecyclerView
        adapter = StudentAdapter(studentList)
        rvStudents.layoutManager = LinearLayoutManager(this)
        rvStudents.adapter = adapter

        // Open QR Scanner
        openQrScanner()

        // Button to reopen QR Scanner
        btnScanQrCode.setOnClickListener {
            it.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).withEndAction {
                it.animate().scaleX(1f).scaleY(1f).setDuration(100)
                openQrScanner()
            }
        }

        // Button to generate CSV
        btnGenerateCsv.setOnClickListener {
            if (studentList.isNotEmpty()) {
                confirmAndGenerateCSV()
            } else {
                Toast.makeText(this, "No attendance data to generate CSV", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to open QR scanner
    private fun openQrScanner() {
        val qrScanner = IntentIntegrator(this)
        qrScanner.setPrompt("Scan Student QR Code")
        qrScanner.setBeepEnabled(true)
        qrScanner.setOrientationLocked(true)
        qrScanner.initiateScan()
    }

    // Handling the QR scan result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null && result.contents != null) {
            val scannedData = result.contents
            val studentDetails = scannedData.split("\n").associate {
                val (key, value) = it.split(": ")
                key to value
            }

            val studentSection = studentDetails["Section"]
            if (studentSection == intent.getStringExtra("SECTION")) {
                val student = Student(
                    name = studentDetails["Name"]!!,
                    rollNumber = studentDetails["RollNo"]!!,
                    section = studentSection!!,
                    registrationNumber = studentDetails["RegNo"]!!
                )
                if (!studentList.any { it.rollNumber == student.rollNumber }) {
                    studentList.add(student)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this, "Student marked present", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Student already marked present", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Incorrect section for this QR", Toast.LENGTH_SHORT).show()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
