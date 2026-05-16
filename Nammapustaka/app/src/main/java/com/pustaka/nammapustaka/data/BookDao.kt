package com.pustaka.nammapustaka.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * FROM books")
    fun getAllBooks(): Flow<List<Book>>

    @Query("SELECT * FROM books WHERE category = :category")
    fun getBooksByCategory(category: String): Flow<List<Book>>

    @Query("SELECT * FROM books WHERE title LIKE '%' || :searchQuery || '%' OR author LIKE '%' || :searchQuery || '%'")
    fun searchBooks(searchQuery: String): Flow<List<Book>>

    @Query("SELECT * FROM books WHERE qrCode = :qrCode LIMIT 1")
    suspend fun getBookByQr(qrCode: String): Book?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)

    @Update
    suspend fun updateBook(book: Book)

    @Query("SELECT * FROM transactions")
    fun getAllTransactions(): Flow<List<Transaction>>

    @Insert
    suspend fun insertTransaction(transaction: Transaction)

    @Query("SELECT studentName, SUM(pagesRead) as totalPages FROM transactions GROUP BY studentName ORDER BY totalPages DESC")
    fun getLeaderboard(): Flow<List<StudentStats>>
}

data class StudentStats(
    val studentName: String,
    val totalPages: Int
)
