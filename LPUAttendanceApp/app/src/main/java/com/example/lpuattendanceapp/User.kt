package com.example.lpuattendanceapp

data class Student(
    val name: String,
    val registrationNumber: String,
    val section: String,
    val rollNumber: String
)

data class Teacher(
    val name: String,
    val uid: String,
    val sections: List<String>
)
