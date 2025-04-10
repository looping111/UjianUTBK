package com.example.ujianutbk

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ujianutbk.data.Student
import com.example.ujianutbk.data.StudentDatabase
import com.example.ujianutbk.databinding.ActivityMainBinding
import com.example.ujianutbk.repository.StudentRepository
import androidx.core.widget.addTextChangedListener
import com.example.ujianutbk.ui.StudentAdapter
import com.example.ujianutbk.ui.StudentViewModel
import com.example.ujianutbk.ui.StudentViewModelFactory
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: StudentAdapter
    private var isEditMode = false
    private var studentToEdit: Student? = null

    private val viewModel: StudentViewModel by viewModels {
        StudentViewModelFactory(StudentRepository(StudentDatabase.getInstance(this).studentDao()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = StudentAdapter(
            onEdit = { student ->
                AlertDialog.Builder(this).apply {
                    setTitle("Edit Siswa")
                    setMessage("Yakin mau edit data ${student.fullName}?")
                    setPositiveButton("Iya") { _, _ ->
                        binding.etNis.setText(student.nis)
                        binding.etName.setText(student.fullName)
                        binding.etNis.isEnabled = false // Biar NIS gak bisa diedit
                        isEditMode = true
                        studentToEdit = student
                    }
                    setNegativeButton("Batal", null)
                }.show()
            },
            onDelete = { student ->
                AlertDialog.Builder(this).apply {
                    setTitle("Hapus Siswa")
                    setMessage("Yakin mau hapus data ${student.fullName}?")
                    setPositiveButton("Hapus") { _, _ ->
                        viewModel.delete(student)
                    }
                    setNegativeButton("Batal", null)
                }.show()
            }
        )

        binding.rvStudents.layoutManager = LinearLayoutManager(this)
        binding.rvStudents.adapter = adapter

        viewModel.allStudents.observe(this) {
            adapter.submitList(it)
        }

        binding.btnAdd.setOnClickListener {
            val nis = binding.etNis.text.toString()
            val name = binding.etName.text.toString()

            if (nis.isNotEmpty() && name.isNotEmpty()) {
                val student = Student(nis, name)
                if (isEditMode) {
                    viewModel.update(student)
                    isEditMode = false
                    studentToEdit = null
                    binding.etNis.isEnabled = true
                } else {
                    viewModel.insert(student)
                }
                binding.etNis.text.clear()
                binding.etName.text.clear()
            }
        }

        binding.etSearch.addTextChangedListener {
            val query = it.toString()
            viewModel.search(query).observe(this) { result ->
                adapter.submitList(result)
            }
        }
    }
}
