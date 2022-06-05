package pt.hugofernandes.kandy

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*




@RunWith(AndroidJUnit4::class)
class FlowExtensionsTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("pt.hugofernandes.kandy.test", appContext.packageName)
    }
}