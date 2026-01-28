package com.ni.router

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File

class VCardRouter {
    fun isVCard(payload: String): Boolean {
        val trimmed = payload.trim()
        return trimmed.startsWith("BEGIN:VCARD", ignoreCase = true) &&
               trimmed.endsWith("END:VCARD", ignoreCase = true)
    }

    fun route(context: Context, payload: String): Boolean {
        try {
            val vcardFile = File(context.cacheDir, "contact.vcf")
            vcardFile.writeText(payload.trim())

            val contentUri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                vcardFile
            )

            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(contentUri, "text/x-vcard")
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
