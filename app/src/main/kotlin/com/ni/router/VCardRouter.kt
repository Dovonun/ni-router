package com.ni.router

import android.content.Context
import android.content.Intent
import android.provider.ContactsContract
import androidx.core.content.FileProvider
import java.io.File

class VCardRouter {
    fun isVCard(payload: String): Boolean {
        val trimmed = payload.trim()
        return trimmed.startsWith("BEGIN:VCARD", ignoreCase = true) &&
               trimmed.endsWith("END:VCARD", ignoreCase = true)
    }

    fun route(context: Context, payload: String): Boolean {
        // Try to parse basic info for ACTION_INSERT (better UX)
        val name = extractField(payload, "FN:")
        val phone = extractField(payload, "TEL:")
        val email = extractField(payload, "EMAIL:")

        if (name != null || phone != null || email != null) {
            val intent = Intent(Intent.ACTION_INSERT).apply {
                type = ContactsContract.Contacts.CONTENT_TYPE
                putExtra(ContactsContract.Intents.Insert.NAME, name)
                putExtra(ContactsContract.Intents.Insert.PHONE, phone)
                putExtra(ContactsContract.Intents.Insert.EMAIL, email)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            return true
        }

        // Fallback to file-based import if parsing fails
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

    private fun extractField(payload: String, prefix: String): String? {
        val lines = payload.lines()
        for (line in lines) {
            if (line.trim().startsWith(prefix, ignoreCase = true)) {
                return line.trim().substring(prefix.length).trim()
            }
        }
        return null
    }
}
