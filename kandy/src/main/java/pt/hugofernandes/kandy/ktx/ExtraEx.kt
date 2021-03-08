package pt.hugofernandes.kandy.ktx

import android.app.Activity
import androidx.fragment.app.Fragment

/**
 * Lazily gets the extra with the given [key]
 *
 * @param key The extra key to use
 * @param default Optional default value
 *
 * Usage example:
 * val bla by extra(EXTRA_KEY, false)
 */
inline fun <reified T: Any> Activity.extra(key: String, default: T? = null) = lazy {
    val value = intent?.extras?.get(key)
    if (value is T) value else default
}

inline fun <reified T: Any> Activity.extraNotNull(key: String, default: T? = null) = lazy {
    val value = intent?.extras?.get(key)
    requireNotNull(if (value is T) value else default) { key }
}

inline fun <reified T: Any> Fragment.extra(key: String, default: T? = null) = lazy {
    val value = arguments?.get(key)
    if (value is T) value else default
}

inline fun <reified T: Any> Fragment.extraNotNull(key: String, default: T? = null) = lazy {
    val value = arguments?.get(key)
    requireNotNull(if (value is T) value else default) { key }
}