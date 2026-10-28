package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProcessingScreen(
    viewModel: MainViewModel,
    onNavigate: (String) -> Unit
) {
    val selectedFileName by viewModel.selectedFileName.collectAsState()
    val selectedPlatform by viewModel.selectedPlatform.collectAsState()
    val detectedProjectType by viewModel.detectedProjectType.collectAsState()
    val processingStage by viewModel.processingStage.collectAsState()
    val progressPercent by viewModel.progressPercent.collectAsState()
    val buildStatus by viewModel.buildStatus.collectAsState()

    val animatedProgress by animateFloatAsState(
        targetValue = progressPercent,
        label = "ProgressAnimation"
    )

    // When buildStatus becomes SUCCESS or FAILED, navigate to build_result automatically or let ViewModel handle it.
    LaunchedEffect(buildStatus) {
        if (buildStatus == "SUCCESS" || buildStatus == "FAILED") {
            // Screen transition is already handled in ViewModel, but we can also ensure navigation if needed.
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Processing Project", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF3B82F6).copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier.size(80.dp),
                    color = Color(0xFF3B82F6),
                    trackColor = Color(0xFF1E293B),
                    strokeWidth = 6.dp,
                )
                Icon(
                    imageVector = Icons.Default.Build,
                    contentDescription = "Building",
                    tint = Color(0xFF3B82F6),
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "${(animatedProgress * 100).toInt()}% Completed",
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = processingStage,
                fontSize = 14.sp,
                color = Color(0xFF06B6D4),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    InfoRow("Original Filename", selectedFileName ?: "project.zip")
                    InfoRow("Target Platform", selectedPlatform)
                    InfoRow("Detected Project Type", detectedProjectType)
                    InfoRow("Job Status", buildStatus)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    viewModel.startProcessingAndBuild()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E293B))
            ) {
                Text("Refresh / Re-run Build Step", color = Color(0xFF3B82F6), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
    }
}
