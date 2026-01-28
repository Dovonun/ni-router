package com.ni.router

import org.junit.Assert.assertEquals
import org.junit.Test

class PayloadRouterTest {
    private val router = PayloadRouter()

    @Test
    fun testGetPayloadFromViewIntent() {
        val payload = "WIFI:S:MyNet;P:password;;"
        assertEquals(payload, router.getPayloadFromIntent("android.intent.action.VIEW", payload, null))
    }

    @Test
    fun testGetPayloadFromSendIntent() {
        val payload = "BEGIN:VCARD\nFN:John Doe\nEND:VCARD"
        assertEquals(payload, router.getPayloadFromIntent("android.intent.action.SEND", null, payload))
    }

    @Test
    fun testGetPayloadFromUnknownIntent() {
        assertEquals(null, router.getPayloadFromIntent("android.intent.action.MAIN", null, null))
    }
}
