package pt.hugofernandes.kandy.ktx

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import pt.hugofernandes.kandy.flow.*

/**
 * Creates a [CoroutineScope] tied to this Activity lifecycle and appropriate for launching
 * [Flow.collect] operations. The collect operations launched in this scope will be automatically
 * repeated on lifecycle event [Lifecycle.State.STARTED].
 * @param block A block which receives a [CoroutineScope] used to launch [Flow.collect] operations.
 */
context(AppCompatActivity)
inline fun AppCompatActivity.collectorScope(
    crossinline block: suspend CoroutineScope.() -> Unit
) = lifecycleAwareCollectorScope(this, Dispatchers.Main.immediate, block)
