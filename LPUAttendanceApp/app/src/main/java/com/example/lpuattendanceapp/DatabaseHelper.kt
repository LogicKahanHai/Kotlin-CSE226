package com.example.lpuattendanceapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "AttendanceApp.db"
        const val DATABASE_VERSION = 1

        // Student Table
        const val TABLE_STUDENT = "Student"
        const val STUDENT_ID = "ID"
        const val STUDENT_NAME = "Name"
        const val STUDENT_REG_NO = "RegistrationNumber"
        const val STUDENT_SECTION = "Section"
        const val STUDENT_ROLL_NO = "RollNumber"

        // Teacher Table
        const val TABLE_TEACHER = "Teacher"
        const val TEACHER_ID = "ID"
        const val TEACHER_NAME = "Name"
        const val TEACHER_UID = "UID"
        const val TEACHER_SECTIONS = "Sections"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createStudentTable = """
            CREATE TABLE $TABLE_STUDENT (
                $STUDENT_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $STUDENT_NAME TEXT,
                $STUDENT_REG_NO TEXT,
                $STUDENT_SECTION TEXT,
                $STUDENT_ROLL_NO TEXT
            )
        """.trimIndent()

        val createTeacherTable = """
            CREATE TABLE $TABLE_TEACHER (
                $TEACHER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $TEACHER_NAME TEXT,
                $TEACHER_UID TEXT,
                $TEACHER_SECTIONS TEXT
            )
        """.trimIndent()

        db.execSQL(createStudentTable)
        db.execSQL(createTeacherTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_STUDENT")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TEACHER")
        onCreate(db)
    }

    fun clearStudentTable() {
        val db = writableDatabase
        db.execSQL("DELETE FROM $TABLE_STUDENT")
        db.close()
    }

    fun clearTeacherTable() {
        val db = writableDatabase
        db.execSQL("DELETE FROM $TABLE_TEACHER")
        db.close()
    }


    // Insert Student
    fun insertStudent(name: String, regNo: String, section: String, rollNo: String): Long {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(STUDENT_NAME, name)
            put(STUDENT_REG_NO, regNo)
            put(STUDENT_SECTION, section)
            put(STUDENT_ROLL_NO, rollNo)
        }
        return db.insert(TABLE_STUDENT, null, contentValues)
    }

    // Insert Teacher
    fun insertTeacher(name: String, uid: String, sections: String): Long {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(TEACHER_NAME, name)
            put(TEACHER_UID, uid)
            put(TEACHER_SECTIONS, sections)
        }
        return db.insert(TABLE_TEACHER, null, contentValues)
    }
}
