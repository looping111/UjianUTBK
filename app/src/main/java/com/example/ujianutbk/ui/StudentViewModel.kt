package com.example.ujianutbk.ui

import androidx.lifecycle.*
import com.example.ujianutbk.data.Student
import com.example.ujianutbk.repository.StudentRepository
import kotlinx.coroutines.launch

class StudentViewModel(private val repo: StudentRepository) : ViewModel() {
    val allStudents: LiveData<List<Student>> = repo.allStudents

    fun insert(student: Student) = viewModelScope.launch {
        repo.insert(student)
    }

    fun update(student: Student) = viewModelScope.launch {
        repo.update(student)
    }

    fun delete(student: Student) = viewModelScope.launch {
        repo.delete(student)
    }

    fun search(query: String): LiveData<List<Student>> = repo.search(query)
}
