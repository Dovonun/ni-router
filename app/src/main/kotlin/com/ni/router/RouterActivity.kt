package com.ni.router

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast

class RouterActivity : Activity() {
    private val TAG = "NiRouter"
    private val payloadRouter = PayloadRouter()
    private val wifiRouter = WifiRouter()
    private val vCardRouter = VCardRouter()
    private val calendarRouter = CalendarRouter()

    private var pendingPayload: String? = null
    private var wifiReceiver: BroadcastReceiver? = null
    private val timeoutHandler = Handler(Looper.getMainLooper())
    private val timeoutRunnable = Runnable {
        if (pendingPayload != null) {
            Log.d(TAG, "WiFi enable timeout reached, finishing")
            cleanupAndFinish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: intent=${intent?.action}")
        handleIntent(intent)
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
                    waitForWifi(payload)
                    shouldFinish = false
                }
                result != WifiRouter.RouteResult.FAILURE
            }
            vCardRouter.isVCard(payload) -> {
                vCardRouter.route(this, payload)
            }
            vCardRouter.isMalformedSingleLine(payload) -> {
                Toast.makeText(this, "Malformed vCard (missing newlines)", Toast.LENGTH_LONG).show()
                true
            }
            calendarRouter.isCalendar(payload) -> {
                calendarRouter.route(this, payload)
            }
            else -> false
        }

        if (!success) {
            Toast.makeText(this, "No valid payload detected", Toast.LENGTH_SHORT).show()
        }

        if (shouldFinish) {
            cleanupAndFinish()
        }
    }

    private fun waitForWifi(payload: String) {
        pendingPayload = payload
        if (wifiReceiver == null) {
            wifiReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    val state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN)
                    Log.d(TAG, "WiFi State changed: $state")
                    if (state == WifiManager.WIFI_STATE_ENABLED) {
                        Log.d(TAG, "WiFi enabled, re-triggering")
                        val p = pendingPayload
                        pendingPayload = null
                        if (p != null) routePayload(p)
                    }
                }
            }
            registerReceiver(wifiReceiver, IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION))
            // 60 second timeout
            timeoutHandler.postDelayed(timeoutRunnable, 60000)
        }
    }

    private fun cleanupAndFinish() {
        Log.d(TAG, "cleanupAndFinish")
        wifiReceiver?.let {
            unregisterReceiver(it)
            wifiReceiver = null
        }
        timeoutHandler.removeCallbacks(timeoutRunnable)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        wifiReceiver?.let {
            unregisterReceiver(it)
            wifiReceiver = null
        }
        timeoutHandler.removeCallbacks(timeoutRunnable)
    }
}