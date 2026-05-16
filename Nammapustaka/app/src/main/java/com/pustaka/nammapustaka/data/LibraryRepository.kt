package com.pustaka.nammapustaka.data

import kotlinx.coroutines.flow.Flow

class LibraryRepository(private val bookDao: BookDao) {
    val allBooks: Flow<List<Book>> = bookDao.getAllBooks()
    val leaderboard: Flow<List<StudentStats>> = bookDao.getLeaderboard()

    fun getBooksByCategory(category: String) = bookDao.getBooksByCategory(category)
    fun searchBooks(query: String) = bookDao.searchBooks(query)
    suspend fun getBookByQr(qr: String) = bookDao.getBookByQr(qr)
    suspend fun insertBook(book: Book) = bookDao.insertBook(book)
    suspend fun updateBook(book: Book) = bookDao.updateBook(book)
    suspend fun borrowBook(book: Book, studentName: String) {
        val updatedBook = book.copy(
            isBorrowed = true,
            studentName = studentName,
            dueDate = System.currentTimeMillis() + (14L * 24 * 60 * 60 * 1000) // 14 days
        )
        bookDao.updateBook(updatedBook)
        bookDao.insertTransaction(
            Transaction(
                bookId = book.id,
                studentName = studentName,
                borrowDate = System.currentTimeMillis()
            )
        )
    }
}
