package com.example.lpuattendanceapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class TeacherHomeActivity : AppCompatActivity() {
    private lateinit var adapter: FileAdapter
    private val fileList = mutableListOf<File>()
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_home)
        dbHelper = DatabaseHelper(this)

        val rvFiles = findViewById<RecyclerView>(R.id.rvFiles)
        val spinnerSection = findViewById<Spinner>(R.id.spinnerSection)
        val spinnerTimeSlot = findViewById<Spinner>(R.id.spinnerTimeSlot)
        val btnSubmitForm = findViewById<Button>(R.id.btnSubmitAttendanceForm)

        val teacherSections = getTeacherSections()
        val sectionAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, teacherSections)
        sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSection.adapter = sectionAdapter

        val timeSlots = listOf(
            "9:00 AM - 10:00 AM", "10:00 AM - 11:00 AM", "11:00 AM - 12:00 PM",
            "12:00 PM - 1:00 PM", "1:00 PM - 2:00 PM", "2:00 PM - 3:00 PM",
            "3:00 PM - 4:00 PM", "4:00 PM - 5:00 PM"
        )

        val timeSlotAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, timeSlots)
        timeSlotAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTimeSlot.adapter = timeSlotAdapter

        btnSubmitForm.setOnClickListener {
            val selectedSection = spinnerSection.selectedItem?.toString()
            val selectedTimeSlot = spinnerTimeSlot.selectedItem?.toString()

            if (selectedSection.isNullOrEmpty() || selectedTimeSlot.isNullOrEmpty()) {
                Toast.makeText(this, "Please select both Section and Time Slot", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, AttendanceActivity::class.java)
                intent.putExtra("SECTION", selectedSection)
                intent.putExtra("TIME_SLOT", selectedTimeSlot)
                startActivity(intent)
            }
        }

        // Load files from the Downloads folder
        loadFiles()

        // Set up RecyclerView
        adapter = FileAdapter(fileList) { file -> openFile(file) }
        rvFiles.layoutManager = LinearLayoutManager(this)
        rvFiles.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        // Refresh file list when activity resumes
        loadFiles()
        adapter.notifyDataSetChanged()
    }

    private fun loadFiles() {
        val downloadsFolder = File(getExternalFilesDir(null), "Attendance")
        downloadsFolder.listFiles()?.let {
            fileList.clear()
            fileList.addAll(it.filter { file -> file.extension == "csv" })
        }
    }

    private fun openFile(file: File) {
        try {
            val uri = androidx.core.content.FileProvider.getUriForFile(
                this,
                "${applicationContext.packageName}.fileprovider",
                file
            )
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "text/csv")
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NO_HISTORY
            }
            startActivity(intent)
        } catch (e: Exception) {
            println(e.message)
            Toast.makeText(this, "Unable to open file", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getTeacherSections(): List<String> {
        val sections = mutableListOf<String>()
        val cursor = dbHelper.readableDatabase.rawQuery(
            "SELECT ${DatabaseHelper.TEACHER_SECTIONS} FROM ${DatabaseHelper.TABLE_TEACHER}",
            null
        )
        if (cursor.moveToFirst()) {
            val sectionsStr = cursor.getString(0)
            sections.addAll(sectionsStr.split(","))
        }
        cursor.close()
        return sections
    }
}


