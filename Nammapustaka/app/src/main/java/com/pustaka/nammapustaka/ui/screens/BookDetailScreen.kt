package com.pustaka.nammapustaka.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun BookDetailScreen(
    bookId: Int,
    viewModel: LibraryViewModel,
    onBack: () -> Unit
) {
    val books by viewModel.allBooks.collectAsState()
    val book = books.find { it.id == bookId } ?: return

    var studentName by remember { mutableStateOf("") }
    var showBorrowDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(book.title) })
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            AsyncImage(
                model = book.coverImageUrl ?: "https://via.placeholder.com/150",
                contentDescription = book.title,
                modifier = Modifier.fillMaxWidth().height(300.dp),
                contentScale = ContentScale.Crop
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(text = book.title, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text(text = "By ${book.author}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Category: ${book.category}", style = MaterialTheme.typography.bodyMedium)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(text = "Summary (Kannada):", fontWeight = FontWeight.Bold)
            Text(text = book.summary)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            if (book.isBorrowed) {
                val isOverdue = book.dueDate?.let { it < System.currentTimeMillis() } ?: false
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (isOverdue) Color(0xFFFFEBEE) else Color(0xFFE8F5E9)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Currently with: ${book.studentName}",
                            fontWeight = FontWeight.Bold,
                            color = if (isOverdue) Color.Red else Color.Unspecified
                        )
                        book.dueDate?.let {
                            Text(text = "Due: ${java.text.SimpleDateFormat("dd/MM/yyyy").format(it)}")
                        }
                    }
                }
            } else {
                Button(
                    onClick = { showBorrowDialog = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Borrow This Book")
                }
            }
        }
    }

    if (showBorrowDialog) {
        AlertDialog(
            onDismissRequest = { showBorrowDialog = false },
            title = { Text("Borrow Book") },
            text = {
                OutlinedTextField(
                    value = studentName,
                    onValueChange = { studentName = it },
                    label = { Text("Student Name") }
                )
            },
            confirmButton = {
                Button(onClick = {
                    if (studentName.isNotBlank()) {
                        viewModel.borrowBook(book, studentName)
                        showBorrowDialog = false
                    }
                }) {
                    Text("Issue")
                }
            }
        )
    }
}
