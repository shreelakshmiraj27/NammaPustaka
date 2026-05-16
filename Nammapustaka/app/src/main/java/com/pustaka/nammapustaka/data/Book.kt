package com.pustaka.nammapustaka.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val author: String,
    val category: String, // Story, Science, History
    val summary: String, // Kannada summary
    val coverImageUrl: String? = null,
    val qrCode: String,
    val isBorrowed: Boolean = false,
    val dueDate: Long? = null,
    val studentName: String? = null,
    val starRating: Float = 0f,
    val review: String? = null
)
