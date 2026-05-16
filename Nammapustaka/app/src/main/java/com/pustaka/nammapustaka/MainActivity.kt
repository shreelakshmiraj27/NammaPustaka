package com.pustaka.nammapustaka

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pustaka.nammapustaka.ui.screens.*
import com.pustaka.nammapustaka.ui.theme.NammaPustakaTheme
import com.pustaka.nammapustaka.ui.viewmodel.LibraryViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NammaPustakaTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    val viewModel: LibraryViewModel = viewModel()
    
    var hasCameraPermission by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { hasCameraPermission = it }
    )

    LaunchedEffect(Unit) {
        launcher.launch(Manifest.permission.CAMERA)
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute in listOf("home", "leaderboard", "scan_borrow")) {
                NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        label = { Text("Home") },
                        selected = currentRoute == "home",
                        onClick = { navController.navigate("home") }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.QrCodeScanner, contentDescription = "Scan") },
                        label = { Text("Borrow") },
                        selected = currentRoute == "scan_borrow",
                        onClick = { navController.navigate("scan_borrow") }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Leaderboard, contentDescription = "Leaderboard") },
                        label = { Text("Leaderboard") },
                        selected = currentRoute == "leaderboard",
                        onClick = { navController.navigate("leaderboard") }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen(
                    viewModel = viewModel,
                    onBookClick = { book -> navController.navigate("detail/${book.id}") },
                    onAddBookClick = { navController.navigate("add_book") }
                )
            }
            composable(
                "detail/{bookId}",
                arguments = listOf(navArgument("bookId") { type = NavType.IntType })
            ) { backStackEntry ->
                val bookId = backStackEntry.arguments?.getInt("bookId") ?: return@composable
                BookDetailScreen(bookId = bookId, viewModel = viewModel, onBack = { navController.popBackStack() })
            }
            composable("scan_borrow") {
                ScannerScreen(
                    onQrScanned = { qr ->
                        // In a real app, we'd lookup the book by QR and navigate to detail or show borrow dialog
                        // For now, let's navigate to a screen or show a toast
                        navController.popBackStack()
                        // Logic to handle borrow by QR could go here
                    },
                    onCancel = { navController.popBackStack() }
                )
            }
            composable("add_book") {
                AddBookScreen(
                    scannedQr = null,
                    viewModel = viewModel,
                    onBookAdded = { navController.popBackStack() },
                    onScanClick = { navController.navigate("scan_add") }
                )
            }
            composable("scan_add") {
                ScannerScreen(
                    onQrScanned = { qr ->
                        navController.navigate("add_book_with_qr/$qr") {
                            popUpTo("add_book") { inclusive = true }
                        }
                    },
                    onCancel = { navController.popBackStack() }
                )
            }
            composable(
                "add_book_with_qr/{qr}",
                arguments = listOf(navArgument("qr") { type = NavType.StringType })
            ) { backStackEntry ->
                val qr = backStackEntry.arguments?.getString("qr")
                AddBookScreen(
                    scannedQr = qr,
                    viewModel = viewModel,
                    onBookAdded = { navController.navigate("home") { popUpTo("home") { inclusive = true } } },
                    onScanClick = { navController.navigate("scan_add") }
                )
            }
            composable("leaderboard") {
                LeaderboardScreen(viewModel = viewModel)
            }
        }
    }
}
