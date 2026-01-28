package com.ni.router

import android.content.Context
import android.net.wifi.WifiNetworkSuggestion
import android.net.wifi.WifiManager
import android.os.Build
import android.widget.Toast

data class WifiConfig(
    val ssid: String,
    val password: String?,
    val type: String?
)

class WifiRouter {
    fun route(context: Context, payload: String): Boolean {
        val config = parsePayload(payload) ?: return false
        
        val suggestionBuilder = WifiNetworkSuggestion.Builder()
            .setSsid(config.ssid)

        if (config.password != null) {
            when (config.type?.uppercase()) {
                "WPA", "WPA2" -> suggestionBuilder.setWpa2Passphrase(config.password)
                "WPA3" -> suggestionBuilder.setWpa3Passphrase(config.password)
            }
        }

        val suggestion = suggestionBuilder.build()
        val suggestionsList = listOf(suggestion)

        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val status = wifiManager.addNetworkSuggestions(suggestionsList)

        if (status == WifiManager.STATUS_NETWORK_SUGGESTIONS_SUCCESS) {
            Toast.makeText(context, "Network suggested: ${config.ssid}", Toast.LENGTH_SHORT).show()
            return true
        }
        
        return false
    }

    fun parsePayload(payload: String): WifiConfig? {
        if (!payload.startsWith("WIFI:", ignoreCase = true)) return null

        val ssid = extractField(payload, "S:") ?: return null
        val password = extractField(payload, "P:")
        val type = extractField(payload, "T:")

        return WifiConfig(ssid, password, type)
    }

    private fun extractField(payload: String, prefix: String): String? {
        val start = payload.indexOf(prefix, ignoreCase = true)
        if (start == -1) return null
        
        val valueStart = start + prefix.length
        val end = payload.indexOf(";", valueStart)
        
        return if (end == -1) {
            payload.substring(valueStart)
        } else {
            payload.substring(valueStart, end)
        }
    }
}