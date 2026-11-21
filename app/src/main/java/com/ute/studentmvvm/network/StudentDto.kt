package com.ute.studentmvvm.network

import com.google.gson.annotations.SerializedName
import com.ute.studentmvvm.model.Student

data class StudentDto(
    @SerializedName("full_name") val fullName: String? = null,
    @SerializedName("student_code") val studentCode: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("gpa") val gpa: Double? = null,
    @SerializedName("major") val major: String? = null
)


fun StudentDto.toEntity(): Student {
    val safeCode = studentCode?.trim().orEmpty().ifEmpty { "UNKNOWN" }
    val safeGpa = (gpa ?: 0.0).coerceIn(0.0, 4.0)
    return Student(
        id = 0,
        fullName = fullName?.trim().orEmpty().ifEmpty { "Chưa cập nhật" },
        studentCode = safeCode,
        email = email?.trim().orEmpty().ifEmpty { "${safeCode.lowercase()}@ute.udn.vn" },
        gpa = safeGpa,
        major = major?.trim().orEmpty().ifEmpty { "Công Nghệ Thông Tin" }
    )
}