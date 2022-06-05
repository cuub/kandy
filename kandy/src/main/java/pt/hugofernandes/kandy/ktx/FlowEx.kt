package pt.hugofernandes.kandy.ktx

import android.app.Service
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import pt.hugofernandes.kandy.flow.*

/**
 * Launches a [Flow.collect] for this [Flow] using a [CoroutineScope] tied to this Activity
 * lifecycle and emits values into the [collector].
 * @param collector The consumer of emitted data.
 */
context(AppCompatActivity)
inline fun <T> Flow<T>.onReceive(crossinline collector: suspend CoroutineScope.(T) -> Unit) =
    lifecycleAwareCollect(lifecycleOwner, Dispatchers.Main.immediate, collector)

/**
 * Launches a [Flow.collect] for this [Flow] using a [CoroutineScope] tied to this Activity
 * lifecycle and emits values into the [collector] where subsequent repetitions of the
 * same value are filtered out using the `distinctUntilChanged` operator.
 * Note that any instance of [StateFlow] already behaves as if `distinctUntilChanged` operator is
 * applied to it, so using [onChange] is effectively the same as using [onReceive].
 * @param collector The consumer of emitted data.
 */
context(AppCompatActivity)
inline fun <T> Flow<T>.onChange(crossinline collector: suspend CoroutineScope.(T) -> Unit) =
    distinctUntilChanged()
        .lifecycleAwareCollect(lifecycleOwner, Dispatchers.Main.immediate, collector)

/**
 * Launches a [Flow.collect] for this [Flow] using a [CoroutineScope] tied to this [Fragment]'s
 * `viewLifecycleOwner`.
 * @param collector The consumer of emitted data.
 */
context(Fragment)
inline fun <T> Flow<T>.onReceive(crossinline collector: suspend CoroutineScope.(T) -> Unit) =
    lifecycleAwareCollect(viewLifecycleOwner, Dispatchers.Main.immediate, collector)

/**
 * Launches a [Flow.collect] for this [Flow] using a [CoroutineScope] tied to this [Fragment]'s
 * `viewLifecycleOwner` and emits values into the [collector] where subsequent repetitions of the
 * same value are filtered out using the `distinctUntilChanged` operator.
 * Note that any instance of [StateFlow] already behaves as if `distinctUntilChanged` operator is
 * applied to it, so using [onChange] is effectively the same as using [onReceive].
 * @param collector The consumer of emitted data.
 */
context(Fragment)
inline fun <T> Flow<T>.onChange(crossinline collector: suspend CoroutineScope.(T) -> Unit) =
    distinctUntilChanged()
        .lifecycleAwareCollect(viewLifecycleOwner, Dispatchers.Main.immediate, collector)

/**
 * Launches a [Flow.collect] for this [Flow] using this [ViewModel]'s [viewModelScope] and emits
 * values into the [collector].
 * @param coroutineDispatcher The dispatcher used to perform the [Flow.collect] operation.
 * @param collector The consumer of emitted data.
 */
context(ViewModel)
inline fun <T> Flow<T>.onReceive(
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    crossinline collector: suspend CoroutineScope.(T) -> Unit
) = viewModelScope.launch(coroutineDispatcher) { collect { collector(it) } }

/**
 * Launches a [Flow.collect] for this [Flow] using this [ViewModel]'s [viewModelScope] and emits
 * values into the [collector] where subsequent repetitions of the same value are filtered out using
 * the `distinctUntilChanged` operator.
 * Note that any instance of [StateFlow] already behaves as if `distinctUntilChanged` operator is
 * applied to it, so using [onChange] is effectively the same as using [onReceive].
 * @param coroutineDispatcher The dispatcher used to perform the [Flow.collect] operation.
 * @param collector The consumer of emitted data.
 */
context(ViewModel)
inline fun <T> Flow<T>.onChange(
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    crossinline collector: suspend CoroutineScope.(T) -> Unit
) = viewModelScope.launch(coroutineDispatcher) { distinctUntilChanged().collect { collector(it) } }

/**
 * Launches a [Flow.collect] for this [Flow] using a [CoroutineScope] tied to this [Service]'s
 * lifecycle and emits values into the [collector].
 * @param coroutineDispatcher The dispatcher used to perform the [Flow.collect] operation.
 * @param collector The consumer of emitted data.
 */
context(LifecycleService)
inline fun <T> Flow<T>.onReceive(
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    crossinline collector: suspend CoroutineScope.(T) -> Unit
) = lifecycleAwareCollect(lifecycleOwner, coroutineDispatcher, collector)

/**
 * Launches a [Flow.collect] for this [Flow] using a [CoroutineScope] tied to this [Service]'s
 * lifecycle and emits values into the [collector] where subsequent repetitions of the same value
 * are filtered out using the `distinctUntilChanged` operator.
 * Note that any instance of [StateFlow] already behaves as if `distinctUntilChanged` operator is
 * applied to it, so using [onChange] is effectively the same as using [onReceive].
 * @param coroutineDispatcher The dispatcher used to perform the [Flow.collect] operation.
 * @param collector The consumer of emitted data.
 */
context(LifecycleService)
inline fun <T> Flow<T>.onChange(
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    crossinline collector: suspend CoroutineScope.(T) -> Unit
) = distinctUntilChanged().lifecycleAwareCollect(lifecycleOwner, coroutineDispatcher, collector)

/**
 * Launches a [Flow.collect] for this [Flow] using a [CoroutineScope] tied to this [View]'s
 * lifecycle and emits values into the [collector].
 * @param collector The consumer of emitted data.
 */
context(View, LifecycleOwner)
inline fun <T> Flow<T>.onReceive(crossinline collector: suspend CoroutineScope.(T) -> Unit) =
    lifecycleAwareCollect(lifecycleOwner, Dispatchers.Main.immediate, collector)

/**
 * Launches a [Flow.collect] for this [Flow] using a [CoroutineScope] tied to this [View]'s
 * lifecycle and emits values into the [collector] where subsequent repetitions of the same value
 * are filtered out using the `distinctUntilChanged` operator.
 * Note that any instance of [StateFlow] already behaves as if `distinctUntilChanged` operator is
 * applied to it, so using [onChange] is effectively the same as using [onReceive].
 * @param collector The consumer of emitted data.
 */
context(View, LifecycleOwner)
inline fun <T> Flow<T>.onChange(
    crossinline collector: suspend CoroutineScope.(T) -> Unit
) = distinctUntilChanged()
    .lifecycleAwareCollect(lifecycleOwner, Dispatchers.Main.immediate, collector)
