package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val currentScreen by viewModel.currentScreen.collectAsState()

                    // Handle system back button gracefully
                    if (currentScreen != "dashboard") {
                        BackHandler {
                            viewModel.navigateTo("dashboard")
                        }
                    }

                    when (currentScreen) {
                        "dashboard" -> DashboardScreen(viewModel = viewModel, onNavigate = { viewModel.navigateTo(it) })
                        "processing" -> ProcessingScreen(viewModel = viewModel, onNavigate = { viewModel.navigateTo(it) })
                        "build_result" -> BuildResultScreen(viewModel = viewModel, onNavigate = { viewModel.navigateTo(it) })
                        "bug_report" -> BugReportScreen(viewModel = viewModel, onNavigate = { viewModel.navigateTo(it) })
                        "history" -> HistoryScreen(viewModel = viewModel, onNavigate = { viewModel.navigateTo(it) })
                        "history_detail" -> HistoryDetailScreen(viewModel = viewModel, onNavigate = { viewModel.navigateTo(it) })
                        "ai_repair" -> AiRepairPromptScreen(viewModel = viewModel, onNavigate = { viewModel.navigateTo(it) })
                        "downloads" -> DownloadManagerScreen(viewModel = viewModel, onNavigate = { viewModel.navigateTo(it) })
                        else -> DashboardScreen(viewModel = viewModel, onNavigate = { viewModel.navigateTo(it) })
                    }
                }
            }
        }
    }
}
