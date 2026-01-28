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
        var name = extractField(payload, "FN")
        if (name == null) {
            val n = extractField(payload, "N")
            if (n != null) {
                // Convert "Last;First;Middle;Prefix;Suffix" to "First Last"
                val parts = n.split(";")
                val first = parts.getOrNull(1)?.trim() ?: ""
                val last = parts.getOrNull(0)?.trim() ?: ""
                name = "$first $last".trim()
            }
        }

        val phone = extractField(payload, "TEL")
        val email = extractField(payload, "EMAIL")
        val org = extractField(payload, "ORG")
        val title = extractField(payload, "TITLE")
        val adr = extractField(payload, "ADR")
        val url = extractField(payload, "URL")

        if (name != null || phone != null || email != null || org != null || title != null) {
            val intent = Intent(Intent.ACTION_INSERT).apply {
                type = ContactsContract.Contacts.CONTENT_TYPE
                putExtra(ContactsContract.Intents.Insert.NAME, name)
                putExtra(ContactsContract.Intents.Insert.PHONE, phone)
                putExtra(ContactsContract.Intents.Insert.EMAIL, email)
                putExtra(ContactsContract.Intents.Insert.COMPANY, org)
                putExtra(ContactsContract.Intents.Insert.JOB_TITLE, title)
                putExtra(ContactsContract.Intents.Insert.POSTAL, formatAddress(adr))
                putExtra(ContactsContract.Intents.Insert.NOTES, url) // URL usually goes to notes in simple INSERT
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            return true
        }

        // Fallback to file-based import if parsing fails
        return try {
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
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun formatAddress(adr: String?): String? {
        if (adr == null) return null
        // ADR: postbox;extended;street;locality;region;postalcode;country
        return adr.replace(";", " ").trim().replace(Regex("\\s+"), " ")
    }

    private fun extractField(payload: String, fieldName: String): String? {
        val lines = payload.lines()
        for (line in lines) {
            val trimmedLine = line.trim()
            if (trimmedLine.startsWith(fieldName, ignoreCase = true)) {
                val firstColon = trimmedLine.indexOf(':')
                if (firstColon != -1 && trimmedLine.substring(0, firstColon).contains(fieldName, ignoreCase = true)) {
                    return trimmedLine.substring(firstColon + 1).trim()
                }
            }
        }
        return null
    }
}