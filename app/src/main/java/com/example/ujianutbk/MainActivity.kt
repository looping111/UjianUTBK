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

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: StudentAdapter

    private val viewModel: StudentViewModel by viewModels {
        StudentViewModelFactory(StudentRepository(StudentDatabase.getInstance(this).studentDao()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = StudentAdapter(
            onEdit = { student ->
                binding.etNis.setText(student.nis)
                binding.etName.setText(student.fullName)
                viewModel.delete(student) // supaya bisa update saat submit lagi
            },
            onDelete = { student ->
                viewModel.delete(student)
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
                viewModel.insert(Student(nis, name))
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
