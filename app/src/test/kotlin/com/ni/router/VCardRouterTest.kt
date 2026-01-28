package com.ni.router

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class VCardRouterTest {
    private val router = VCardRouter()

    @Test
    fun testIsVCard() {
        assertTrue(router.isVCard("BEGIN:VCARD\nVERSION:3.0\nFN:John Doe\nEND:VCARD"))
        assertTrue(router.isVCard("  BEGIN:VCARD\r\nFN:Test\nEND:VCARD  "))
        assertFalse(router.isVCard("WIFI:S:MyNet;;"))
        assertFalse(router.isVCard("BEGIN:VEVENT\nSUMMARY:Meeting\nEND:VEVENT"))
    }
}
