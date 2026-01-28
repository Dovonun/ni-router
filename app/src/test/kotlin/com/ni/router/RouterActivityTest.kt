package com.ni.router

import org.junit.Assert.assertNotNull
import org.junit.Test

class RouterActivityTest {
    @Test
    fun testActivityInstantiation() {
        // We can't easily test Activity lifecycle without Robolectric,
        // but we can at least verify the class exists and can be instantiated
        // (though in Android it might fail due to stubbed JARs)
        val activity = RouterActivity()
        assertNotNull(activity)
    }
}
