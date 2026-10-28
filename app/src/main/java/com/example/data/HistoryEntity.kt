package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "processing_history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val fileName: String,
    val fileSize: String,
    val timestamp: Long = System.currentTimeMillis(),
    val targetPlatform: String,
    val detectedProjectType: String,
    val processingStatus: String, // SUCCESS, FAILED, QUEUED, etc.
    val buildResult: String,
    val errorCount: Int,
    val warningCount: Int,
    val reportSummary: String,
    val buildLogs: String,
    val apkArtifactName: String?,
    val apkArtifactSize: String?,
    val aiRepairPrompt: String?
)
