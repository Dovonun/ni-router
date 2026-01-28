package com.ni.router

import android.content.Context
import android.content.Intent
import android.net.wifi.WifiNetworkSuggestion
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.os.Bundle
import android.widget.Toast

data class WifiConfig(
    val ssid: String,
    val password: String?,
    val type: String?
)

class WifiRouter {
    enum class RouteResult {
        SUCCESS,
        ALREADY_CONNECTED,
        WAITING_FOR_WIFI,
        FAILURE
    }

    fun route(context: Context, payload: String): RouteResult {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager

        if (payload.equals("WIFI:CLEAR", ignoreCase = true)) {
            wifiManager.removeNetworkSuggestions(emptyList())
            Toast.makeText(context, "Lingering network suggestions cleared.", Toast.LENGTH_SHORT).show()
            return RouteResult.SUCCESS
        }

        val config = parsePayload(payload) ?: return RouteResult.FAILURE
        
        // Check if already connected (best effort without location permission)
        val info = wifiManager.connectionInfo
        val currentSsid = info.ssid?.replace("\"", "")
        if (currentSsid != null && currentSsid != "<unknown ssid>" && currentSsid.equals(config.ssid, ignoreCase = true)) {
            Toast.makeText(context, "Already connected to ${config.ssid}", Toast.LENGTH_SHORT).show()
            return RouteResult.ALREADY_CONNECTED
        }

        if (!wifiManager.isWifiEnabled) {
            val panelIntent = Intent("android.settings.panel.action.WIFI").apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(panelIntent)
            return RouteResult.WAITING_FOR_WIFI
        }

        // Use ACTION_WIFI_ADD_NETWORKS (API 30+) for a "stateless" saved network experience
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val suggestionBuilder = WifiNetworkSuggestion.Builder()
                .setSsid(config.ssid)

            if (config.password != null) {
                when (config.type?.uppercase()) {
                    "WPA", "WPA2" -> suggestionBuilder.setWpa2Passphrase(config.password)
                    "WPA3" -> suggestionBuilder.setWpa3Passphrase(config.password)
                }
            }

            val suggestion = suggestionBuilder.build()
            val intent = Intent(Settings.ACTION_WIFI_ADD_NETWORKS).apply {
                val bundle = Bundle()
                bundle.putParcelableArrayList("android.provider.extra.WIFI_NETWORK_LIST", arrayListOf(suggestion))
                putExtras(bundle)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            return RouteResult.SUCCESS
        } else {
            // Fallback for API 29
            val suggestionBuilder = WifiNetworkSuggestion.Builder()
                .setSsid(config.ssid)
            if (config.password != null && config.type?.uppercase() != "WPA3") {
                suggestionBuilder.setWpa2Passphrase(config.password)
            }
            val suggestion = suggestionBuilder.build()
            wifiManager.addNetworkSuggestions(listOf(suggestion))
            
            val settingsIntent = Intent(Settings.ACTION_WIFI_SETTINGS).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(settingsIntent)
            return RouteResult.SUCCESS
        }
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
