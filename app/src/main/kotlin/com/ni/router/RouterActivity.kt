package com.ni.router

import android.app.Activity
import android.content.Intent
import android.os.Bundle

class RouterActivity : Activity() {
    private val payloadRouter = PayloadRouter()
    private val wifiRouter = WifiRouter()
    private val vCardRouter = VCardRouter()
    private val calendarRouter = CalendarRouter()

    private var pendingPayload: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        // If we were waiting for WiFi to turn on, try again
        pendingPayload?.let {
            val wifiManager = getSystemService(WIFI_SERVICE) as android.net.wifi.WifiManager
            if (wifiManager.isWifiEnabled) {
                val payload = it
                pendingPayload = null
                routePayload(payload)
            }
        }
    }

    private fun handleIntent(intent: Intent?) {
        if (intent == null) {
            finish()
            return
        }
        
        val payload = payloadRouter.getPayloadFromIntent(
            intent.action,
            intent.dataString,
            intent.getStringExtra(Intent.EXTRA_TEXT)
        )
        
        if (payload != null) {
            routePayload(payload)
        } else {
            finish()
        }
    }

    private fun routePayload(payload: String) {
        var shouldFinish = true
        val success = when {
            payload.startsWith("WIFI:", ignoreCase = true) || payload.equals("WIFI:CLEAR", ignoreCase = true) -> {
                val result = wifiRouter.route(this, payload)
                if (result == WifiRouter.RouteResult.WAITING_FOR_WIFI) {
                    pendingPayload = payload
                    shouldFinish = false
                }
                result != WifiRouter.RouteResult.FAILURE
            }
            vCardRouter.isVCard(payload) -> vCardRouter.route(this, payload)
            vCardRouter.isMalformedSingleLine(payload) -> {
                android.widget.Toast.makeText(this, "Malformed vCard (missing newlines)", android.widget.Toast.LENGTH_LONG).show()
                true
            }
            calendarRouter.isCalendar(payload) -> calendarRouter.route(this, payload)
            else -> false
        }

        if (!success) {
            android.widget.Toast.makeText(this, "No valid payload detected", android.widget.Toast.LENGTH_SHORT).show()
        }

        if (shouldFinish) {
            finish()
        }
    }
}
