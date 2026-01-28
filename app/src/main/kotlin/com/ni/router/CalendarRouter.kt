package com.ni.router

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File

class CalendarRouter {
    fun isCalendar(payload: String): Boolean {
        val trimmed = payload.trim()
        return (trimmed.startsWith("BEGIN:VCALENDAR", ignoreCase = true) && trimmed.endsWith("END:VCALENDAR", ignoreCase = true)) ||
               (trimmed.startsWith("BEGIN:VEVENT", ignoreCase = true) && trimmed.endsWith("END:VEVENT", ignoreCase = true))
    }

    fun route(context: Context, payload: String): Boolean {
        try {
            val icsFile = File(context.cacheDir, "event.ics")
            icsFile.writeText(payload.trim())

            val contentUri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                icsFile
            )

            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(contentUri, "text/calendar")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            context.startActivity(intent)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}
