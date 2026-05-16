package com.pustaka.nammapustaka.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pustaka.nammapustaka.data.Book
import com.pustaka.nammapustaka.ui.viewmodel.LibraryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: LibraryViewModel,
    onBookClick: (Book) -> Unit,
    onAddBookClick: () -> Unit
) {
    val books by viewModel.allBooks.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Namma Pustaka") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddBookClick) {
                Icon(Icons.Default.Add, contentDescription = "Add Book")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search by name or author") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("All", "Story", "Science", "History").forEach { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = { viewModel.updateCategory(category) },
                        label = { Text(category) }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(books) { book ->
                    BookCard(book = book, onClick = { onBookClick(book) })
                }
            }
        }
    }
}

@Composable
fun BookCard(book: Book, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            AsyncImage(
                model = book.coverImageUrl ?: "https://via.placeholder.com/150",
                contentDescription = book.title,
                modifier = Modifier.fillMaxWidth().height(180.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = book.title, fontWeight = FontWeight.Bold, maxLines = 1)
                Text(text = book.author, style = MaterialTheme.typography.bodySmall, maxLines = 1)
                
                if (book.isBorrowed) {
                    val isOverdue = book.dueDate?.let { it < System.currentTimeMillis() } ?: false
                    Text(
                        text = "Borrowed by ${book.studentName}",
                        color = if (isOverdue) Color.Red else Color.Unspecified,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}
