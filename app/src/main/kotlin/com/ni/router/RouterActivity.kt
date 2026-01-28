package com.ni.router

import android.app.Activity
import android.content.Intent
import android.os.Bundle

class RouterActivity : Activity() {
    private val payloadRouter = PayloadRouter()

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
        // To be implemented in next phases
    }
}
