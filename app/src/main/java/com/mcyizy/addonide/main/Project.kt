package com.mcyizy.addonide.main

import java.io.File
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

public class Project {

    //获取自上次修改以来的格式化时间
    public fun getFormattedTimeSinceLastModification(filePath: String): String {
        try {
            val file = File(filePath)
            val lastModified = file.lastModified() / 1000  // 将毫秒数转换为秒数
    
            val currentDateTime = LocalDateTime.now()
            val fileModifiedDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(lastModified), ZoneId.systemDefault())

            val duration = Duration.between(fileModifiedDateTime, currentDateTime)
            
            val seconds = duration.toMillis() / 1000

            return when {
                seconds < 60 -> "修改: ${seconds}秒钟前"
                seconds < 3600 -> "修改: ${seconds / 60}分钟前"
                seconds < 86400 -> "修改: ${seconds / 3600}小时前"
                else -> {
                    val days = duration.toDays()
                    "修改: ${days}天前"
                }
            }
            } catch (e: Exception) {
            e.printStackTrace()
            return "发生错误！"
        }
    }

}