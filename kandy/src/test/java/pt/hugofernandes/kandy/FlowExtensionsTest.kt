package pt.hugofernandes.kandy

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.*
import org.junit.*
import kotlin.test.assertContentEquals

@OptIn(ExperimentalCoroutinesApi::class)
class FlowExtensionsTest {
    private val coroutineDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(coroutineDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Flow is collected in ViewModel with onReceive`() = runTest {
        val collectedOnMain = FlowyViewModel(Dispatchers.Main).numbers.toList()
        assertContentEquals(FlowyViewModel.Numbers, collectedOnMain)

        val collectedOnIO = FlowyViewModel(Dispatchers.IO).numbers.toList()
        assertContentEquals(FlowyViewModel.Numbers, collectedOnIO)
    }
}
