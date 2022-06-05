package pt.hugofernandes.kandy.ktx

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import pt.hugofernandes.kandy.flow.*

/**
 * Creates a [CoroutineScope] tied to this Activity lifecycle and appropriate for launching
 * [Flow.collect] operations. The collect operations launched in this scope will be automatically
 * repeated on lifecycle event [Lifecycle.State.STARTED].
 * @param coroutineDispatcher The [CoroutineDispatcher] used to create this [CoroutineScope].
 * @param block A block which receives a [CoroutineScope] used to launch [Flow.collect] operations.
 */
context(AppCompatActivity)
inline fun AppCompatActivity.collectorScope(
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    crossinline block: suspend CoroutineScope.() -> Unit
) = lifecycleAwareCollectorScope(this, coroutineDispatcher, block)


/**
 * Creates a [CoroutineScope] tied to this [Fragment]'s `viewLifecycleOwner` and appropriate for
 * launching [Flow.collect] operations. The collect operations launched in this scope will be
 * automatically repeated on lifecycle event [Lifecycle.State.STARTED].
 * @param coroutineDispatcher The [CoroutineDispatcher] used to create this [CoroutineScope].
 * @param block A block which receives a [CoroutineScope] used to launch [Flow.collect] operations.
 */
inline fun Fragment.collectorScope(
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    crossinline block: suspend CoroutineScope.() -> Unit
) = lifecycleAwareCollectorScope(viewLifecycleOwner, coroutineDispatcher, block)

/**
 * Creates a [CoroutineScope] tied to this [View]'s lifecycle  and appropriate for
 * launching [Flow.collect] operations. The collect operations launched in this scope will be
 * automatically repeated on lifecycle event [Lifecycle.State.STARTED].
 * @param coroutineDispatcher The [CoroutineDispatcher] used to create this [CoroutineScope].
 * @param block A block which receives a [CoroutineScope] used to launch [Flow.collect] operations.
 */
context(View, LifecycleOwner)
inline fun <T> Flow<T>.collectorScope(
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    crossinline block: suspend CoroutineScope.() -> Unit
) = lifecycleAwareCollectorScope(lifecycleOwner, coroutineDispatcher, block)
