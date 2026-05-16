package com.pustaka.nammapustaka.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val bookId: Int,
    val studentName: String,
    val borrowDate: Long,
    val returnDate: Long? = null,
    val pagesRead: Int = 0
)
