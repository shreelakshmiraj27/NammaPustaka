package com.pustaka.nammapustaka.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pustaka.nammapustaka.data.Book
import com.pustaka.nammapustaka.ui.viewmodel.LibraryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookScreen(
    scannedQr: String?,
    viewModel: LibraryViewModel,
    onBookAdded: () -> Unit,
    onScanClick: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var summary by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Story") }
    var qrCode by remember { mutableStateOf(scannedQr ?: "") }

    LaunchedEffect(scannedQr) {
        if (scannedQr != null) qrCode = scannedQr
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Add New Book") }) }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).padding(16.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Book Title") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = author, onValueChange = { author = it }, label = { Text("Author") }, modifier = Modifier.fillMaxWidth())
            
            Text("Category")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Story", "Science", "History").forEach { cat ->
                    FilterChip(
                        selected = category == cat,
                        onClick = { category = cat },
                        label = { Text(cat) }
                    )
                }
            }
            
            OutlinedTextField(value = summary, onValueChange = { summary = it }, label = { Text("Summary (Kannada)") }, modifier = Modifier.fillMaxWidth(), minLines = 3)
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = qrCode, onValueChange = { qrCode = it }, label = { Text("QR Code") }, modifier = Modifier.weight(1f))
                Button(onClick = onScanClick, modifier = Modifier.padding(top = 8.dp)) {
                    Text("Scan")
                }
            }

            Button(
                onClick = {
                    if (title.isNotBlank() && author.isNotBlank() && qrCode.isNotBlank()) {
                        viewModel.addBook(Book(title = title, author = author, summary = summary, category = category, qrCode = qrCode))
                        onBookAdded()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add to Library")
            }
        }
    }
}
