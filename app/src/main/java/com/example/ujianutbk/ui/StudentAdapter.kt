package com.example.ujianutbk.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ujianutbk.R
import com.example.ujianutbk.data.Student

class StudentAdapter(
    private val onEdit: (Student) -> Unit,
    private val onDelete: (Student) -> Unit
) : ListAdapter<Student, StudentAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNis: TextView = view.findViewById(R.id.tvNis)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val btnEdit: ImageButton = view.findViewById(R.id.btnEdit)
        val btnDelete: ImageButton = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = getItem(position)
        holder.tvNis.text = student.nis
        holder.tvName.text = student.fullName
        holder.btnEdit.setOnClickListener { onEdit(student) }
        holder.btnDelete.setOnClickListener { onDelete(student) }
    }

    class DiffCallback : DiffUtil.ItemCallback<Student>() {
        override fun areItemsTheSame(old: Student, new: Student) = old.nis == new.nis
        override fun areContentsTheSame(old: Student, new: Student) = old == new
    }
}
