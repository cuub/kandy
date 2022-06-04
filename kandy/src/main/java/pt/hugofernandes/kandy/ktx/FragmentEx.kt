package pt.hugofernandes.kandy.ktx

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import pt.hugofernandes.kandy.flow.*

/**
 * Creates a [CoroutineScope] tied to this [Fragment]'s `viewLifecycleOwner` and appropriate for
 * launching [Flow.collect] operations. The collect operations launched in this scope will be
 * automatically repeated on lifecycle event [Lifecycle.State.STARTED].
 * @param block A block which receives a [CoroutineScope] used to launch [Flow.collect] operations.
 */
inline fun Fragment.collectorScope(
    crossinline block: suspend CoroutineScope.() -> Unit
) = lifecycleAwareCollectorScope(viewLifecycleOwner, Dispatchers.Main.immediate, block)
