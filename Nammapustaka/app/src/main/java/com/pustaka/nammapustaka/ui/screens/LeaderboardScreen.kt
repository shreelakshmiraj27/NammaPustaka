package com.pustaka.nammapustaka.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pustaka.nammapustaka.ui.viewmodel.LibraryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen(viewModel: LibraryViewModel) {
    val stats by viewModel.leaderboard.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Reading Leaderboard") }) }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
            itemsIndexed(stats) { index, student ->
                ListItem(
                    headlineContent = { Text(student.studentName, fontWeight = FontWeight.Bold) },
                    trailingContent = { Text("${student.totalPages} Pages", style = MaterialTheme.typography.titleMedium) },
                    leadingContent = {
                        Text(
                            text = "${index + 1}",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.width(32.dp)
                        )
                    }
                )
                HorizontalDivider()
            }
        }
    }
}
