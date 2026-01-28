package com.ni.router

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CalendarRouterTest {
    private val router = CalendarRouter()

    @Test
    fun testIsCalendar() {
        assertTrue(router.isCalendar("BEGIN:VCALENDAR\nVERSION:2.0\nBEGIN:VEVENT\nSUMMARY:Meeting\nEND:VEVENT\nEND:VCALENDAR"))
        assertTrue(router.isCalendar("BEGIN:VEVENT\nSUMMARY:Quick Meeting\nEND:VEVENT"))
        assertTrue(router.isCalendar("  BEGIN:VCALENDAR\r\nPRODID:Test\nEND:VCALENDAR  "))
        assertFalse(router.isCalendar("BEGIN:VCARD\nFN:John\nEND:VCARD"))
    }
}
