package com.ni.router

import org.junit.Assert.assertEquals
import org.junit.Test

class WifiRouterTest {
    private val router = WifiRouter()

    @Test
    fun testParseWifiPayload() {
        val payload = "WIFI:T:WPA;S:MyNetwork;P:password;;"
        val config = router.parsePayload(payload)
        assertEquals("MyNetwork", config?.ssid)
        assertEquals("password", config?.password)
        assertEquals("WPA", config?.type)
    }

    @Test
    fun testParseWifiPayloadNoPassword() {
        val payload = "WIFI:T:nopass;S:OpenNet;;"
        val config = router.parsePayload(payload)
        assertEquals("OpenNet", config?.ssid)
        assertEquals(null, config?.password)
        assertEquals("nopass", config?.type)
    }

    @Test
    fun testParseInvalidPayload() {
        val payload = "NOTWIFI:..."
        val config = router.parsePayload(payload)
        assertEquals(null, config)
    }
}
