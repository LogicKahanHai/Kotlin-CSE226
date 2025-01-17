package com.example.attendanceapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "AttendanceDB"
        private const val DATABASE_VERSION = 1

        // Table Names
        const val TABLE_USERS = "users"
        const val TABLE_ATTENDANCE = "attendance"

        // Common Column Names
        const val KEY_ID = "id"

        // Users Table Columns
        const val KEY_USERNAME = "username"
        const val KEY_PASSWORD = "password"
        const val KEY_USER_TYPE = "user_type"

        // Attendance Table Columns
        const val KEY_STUDENT_ID = "student_id"
        const val KEY_DATE = "date"
        const val KEY_TIME = "time"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE_USERS = """
            CREATE TABLE $TABLE_USERS (
                $KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $KEY_USERNAME TEXT,
                $KEY_PASSWORD TEXT,
                $KEY_USER_TYPE TEXT
            )
        """.trimIndent()

        val CREATE_TABLE_ATTENDANCE = """
            CREATE TABLE $TABLE_ATTENDANCE (
                $KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $KEY_STUDENT_ID INTEGER,
                $KEY_DATE TEXT,
                $KEY_TIME TEXT,
                FOREIGN KEY($KEY_STUDENT_ID) REFERENCES $TABLE_USERS($KEY_ID)
            )
        """.trimIndent()

        db.execSQL(CREATE_TABLE_USERS)
        db.execSQL(CREATE_TABLE_ATTENDANCE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ATTENDANCE")
        onCreate(db)
    }
}
