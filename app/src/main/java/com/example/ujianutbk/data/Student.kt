package com.example.ujianutbk.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student")
data class Student(
    @PrimaryKey val nis: String,
    val fullName: String
)

