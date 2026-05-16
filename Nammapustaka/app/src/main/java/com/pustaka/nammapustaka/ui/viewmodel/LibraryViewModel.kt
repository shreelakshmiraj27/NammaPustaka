package com.pustaka.nammapustaka.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pustaka.nammapustaka.data.AppDatabase
import com.pustaka.nammapustaka.data.Book
import com.pustaka.nammapustaka.data.LibraryRepository
import com.pustaka.nammapustaka.data.StudentStats
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LibraryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: LibraryRepository
    val allBooks: StateFlow<List<Book>>
    val leaderboard: StateFlow<List<StudentStats>>

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory = _selectedCategory.asStateFlow()

    init {
        val bookDao = AppDatabase.getDatabase(application).bookDao()
        repository = LibraryRepository(bookDao)
        allBooks = combine(repository.allBooks, _searchQuery, _selectedCategory) { books, query, category ->
            books.filter { book ->
                (category == "All" || book.category == category) &&
                (book.title.contains(query, ignoreCase = true) || book.author.contains(query, ignoreCase = true))
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
        
        leaderboard = repository.leaderboard.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun updateCategory(category: String) {
        _selectedCategory.value = category
    }

    fun borrowBook(book: Book, studentName: String) {
        viewModelScope.launch {
            repository.borrowBook(book, studentName)
        }
    }

    fun addBook(book: Book) {
        viewModelScope.launch {
            repository.insertBook(book)
        }
    }
    
    suspend fun getBookByQr(qr: String) = repository.getBookByQr(qr)
}
