package com.ni.router

import android.app.Activity
import android.content.Intent
import android.os.Bundle

class RouterActivity : Activity() {
    private val payloadRouter = PayloadRouter()
    private val wifiRouter = WifiRouter()
    private val vCardRouter = VCardRouter()
    private val calendarRouter = CalendarRouter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleIntent(intent)
        finish()
    }

    private fun handleIntent(intent: Intent?) {
        if (intent == null) return
        
        val payload = payloadRouter.getPayloadFromIntent(
            intent.action,
            intent.dataString,
            intent.getStringExtra(Intent.EXTRA_TEXT)
        )
        
        if (payload != null) {
            routePayload(payload)
        }
    }

    private fun routePayload(payload: String) {
        val success = when {
            payload.startsWith("WIFI:", ignoreCase = true) -> wifiRouter.route(this, payload)
            vCardRouter.isVCard(payload) -> vCardRouter.route(this, payload)
            calendarRouter.isCalendar(payload) -> calendarRouter.route(this, payload)
            else -> false
        }

        if (!success) {
            android.widget.Toast.makeText(this, "No valid payload detected", android.widget.Toast.LENGTH_SHORT).show()
        }
    }
}
