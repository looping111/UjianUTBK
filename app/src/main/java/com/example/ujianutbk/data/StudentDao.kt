package com.example.ujianutbk.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StudentDao {
    @Query("SELECT * FROM student ORDER BY fullName ASC")
    fun getAll(): LiveData<List<Student>>

    @Query("SELECT * FROM student WHERE fullName LIKE :query OR nis LIKE :query")
    fun search(query: String): LiveData<List<Student>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(student: Student)

    @Update
    suspend fun update(student: Student)

    @Delete
    suspend fun delete(student: Student)
}
