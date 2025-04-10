package com.example.ujianutbk.repository

import androidx.lifecycle.LiveData
import com.example.ujianutbk.data.Student
import com.example.ujianutbk.data.StudentDao

class StudentRepository(private val dao: StudentDao) {
    val allStudents: LiveData<List<Student>> = dao.getAll()

    suspend fun insert(student: Student) = dao.insert(student)
    suspend fun update(student: Student) = dao.update(student)
    suspend fun delete(student: Student) = dao.delete(student)

    fun search(query: String): LiveData<List<Student>> = dao.search("%$query%")
}
