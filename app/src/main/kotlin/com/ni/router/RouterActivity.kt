package com.ni.router

import android.app.Activity
import android.content.Intent
import android.os.Bundle

import android.util.Log

class RouterActivity : Activity() {
    private val TAG = "NiRouter"
    private val payloadRouter = PayloadRouter()
    private val wifiRouter = WifiRouter()
    private val vCardRouter = VCardRouter()
    private val calendarRouter = CalendarRouter()

    private var pendingPayload: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: intent=${intent?.action}")
        handleIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: pendingPayload=$pendingPayload")
        // If we were waiting for WiFi to turn on, try again
        pendingPayload?.let {
            val wifiManager = getSystemService(WIFI_SERVICE) as android.net.wifi.WifiManager
            if (wifiManager.isWifiEnabled) {
                Log.d(TAG, "WiFi enabled, re-triggering payload")
                val payload = it
                pendingPayload = null
                routePayload(payload)
            } else {
                Log.d(TAG, "WiFi still disabled, finishing")
                finish()
            }
        }
    }

    private fun handleIntent(intent: Intent?) {
        if (intent == null) {
            Log.d(TAG, "handleIntent: null intent")
            finish()
            return
        }
        
        val payload = payloadRouter.getPayloadFromIntent(
            intent.action,
            intent.dataString,
            intent.getStringExtra(Intent.EXTRA_TEXT)
        )
        
        Log.d(TAG, "handleIntent: payload=$payload")
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
                Log.d(TAG, "routePayload (WIFI): result=$result")
                if (result == WifiRouter.RouteResult.WAITING_FOR_WIFI) {
                    pendingPayload = payload
                    shouldFinish = false
                }
                result != WifiRouter.RouteResult.FAILURE
            }
            vCardRouter.isVCard(payload) -> {
                Log.d(TAG, "routePayload (VCARD)")
                vCardRouter.route(this, payload)
            }
            vCardRouter.isMalformedSingleLine(payload) -> {
                Log.d(TAG, "routePayload (VCARD_MALFORMED)")
                android.widget.Toast.makeText(this, "Malformed vCard (missing newlines)", android.widget.Toast.LENGTH_LONG).show()
                true
            }
            calendarRouter.isCalendar(payload) -> {
                Log.d(TAG, "routePayload (CALENDAR)")
                calendarRouter.route(this, payload)
            }
            else -> {
                Log.d(TAG, "routePayload (UNKNOWN)")
                false
            }
        }

        if (!success) {
            android.widget.Toast.makeText(this, "No valid payload detected", android.widget.Toast.LENGTH_SHORT).show()
        }

        if (shouldFinish) {
            Log.d(TAG, "routePayload: finishing activity")
            finish()
        }
    }
}
