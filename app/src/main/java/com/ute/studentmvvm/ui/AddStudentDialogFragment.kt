package com.ute.studentmvvm.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.ute.studentmvvm.databinding.DialogAddStudentBinding
import com.ute.studentmvvm.model.Student
import com.ute.studentmvvm.viewmodel.StudentViewModel
import java.util.Locale

class AddStudentDialogFragment : DialogFragment() {

    private val viewModel: StudentViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogAddStudentBinding.inflate(LayoutInflater.from(requireContext()))

        return AlertDialog.Builder(requireContext())
            .setTitle("Thêm Sinh Viên Mới")
            .setView(binding.root)
            .setPositiveButton("Lưu vào Room") { _, _ ->
                val name = binding.etFullName.text?.toString().orEmpty().trim()
                val code = binding.etStudentCode.text?.toString().orEmpty().trim()
                val email = binding.etEmail.text?.toString().orEmpty().trim()
                val gpaText = binding.etGpa.text?.toString().orEmpty().trim()

                if (name.isEmpty() || code.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Họ tên và MSSV không được để trống!",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setPositiveButton
                }

                val gpa = if (gpaText.isEmpty()) 0.0 else gpaText.toDoubleOrNull()
                if (gpa == null || gpa < 0.0 || gpa > 4.0) {
                    Toast.makeText(
                        requireContext(),
                        "Điểm GPA không hợp lệ (nhập từ 0.0 đến 4.0)!",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setPositiveButton
                }

                val student = Student(
                    fullName = name,
                    studentCode = code,
                    email = if (email.isNotEmpty()) email else "${code.lowercase(Locale.ROOT)}@ute.udn.vn",
                    gpa = gpa
                )
                viewModel.insert(student)
            }
            .setNegativeButton("Hủy", null)
            .create()
    }
}