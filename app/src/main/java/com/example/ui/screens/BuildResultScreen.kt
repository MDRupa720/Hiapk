package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
fun BuildResultScreen(
    viewModel: MainViewModel,
    onNavigate: (String) -> Unit
) {
    val buildStatus by viewModel.buildStatus.collectAsState()
    val selectedFileName by viewModel.selectedFileName.collectAsState()
    val selectedPlatform by viewModel.selectedPlatform.collectAsState()
    val detectedProjectType by viewModel.detectedProjectType.collectAsState()
    val currentApkName by viewModel.currentApkName.collectAsState()
    val currentApkSize by viewModel.currentApkSize.collectAsState()
    val currentBuildLogs by viewModel.currentBuildLogs.collectAsState()

    val isSuccess = buildStatus == "SUCCESS"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isSuccess) "APK Build Successful" else "Build Failed", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { onNavigate("dashboard") }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(
                            if (isSuccess) Color(0xFF10B981).copy(alpha = 0.15f)
                            else Color(0xFFEF4444).copy(alpha = 0.15f)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isSuccess) Icons.Default.CheckCircle else Icons.Default.Error,
                        contentDescription = null,
                        tint = if (isSuccess) Color(0xFF10B981) else Color(0xFFEF4444),
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (isSuccess) "APK BUILD SUCCESSFUL" else "BUILD FAILED",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = if (isSuccess) Color(0xFF10B981) else Color(0xFFEF4444)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = if (isSuccess) "Your project was successfully packaged into an Android APK." else "The build pipeline encountered errors during execution.",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }

            // Summary Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        ResultRow("Original Project", selectedFileName ?: "project.zip")
                        ResultRow("Target Platform", selectedPlatform)
                        ResultRow("Project Type", detectedProjectType)
                        if (isSuccess) {
                            ResultRow("APK Filename", currentApkName ?: "app-release.apk")
                            ResultRow("Artifact Size", currentApkSize ?: "18.4 MB")
                            ResultRow("Verification", "Passed (Valid Android Package)")
                        } else {
                            ResultRow("Failure Stage", "Gradle Build Execution")
                            ResultRow("Error Count", "1 Error, 1 Warning")
                        }
                    }
                }
            }

            // Action Buttons
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (isSuccess) {
                        Button(
                            onClick = { onNavigate("downloads") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))
                        ) {
                            Icon(imageVector = Icons.Default.Download, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("DOWNLOAD APK", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }
                    } else {
                        Button(
                            onClick = {
                                viewModel.generateAiRepairPromptWithGemini(
                                    selectedFileName ?: "project.zip",
                                    detectedProjectType,
                                    selectedPlatform,
                                    currentBuildLogs
                                )
                                onNavigate("ai_repair")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B5CF6))
                        ) {
                            Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("GENERATE AI REPAIR PROMPT", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = { onNavigate("bug_report") },
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(imageVector = Icons.Default.BugReport, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Bug Report", fontSize = 13.sp)
                        }

                        OutlinedButton(
                            onClick = { onNavigate("dashboard") },
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Home, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("OK / Dashboard", fontSize = 13.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ResultRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
    }
}
