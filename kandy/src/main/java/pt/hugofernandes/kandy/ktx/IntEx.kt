package pt.hugofernandes.kandy.ktx

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import androidx.core.content.ContextCompat

/**
 * Returns the value in density-independent pixels
 */
inline val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()


/**
 * Returns the value in pixels
 */
inline val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()


/**
 * Returns a color associated with a particular resource ID
 */
inline fun Int.toColor(context: Context) = ContextCompat.getColor(context, this)

inline  fun Int.toColorStateList() = ColorStateList.valueOf(this)

/**
 * Converts an [Int] to [Boolean].
 * Returns true if value is 1. Otherwise, false
 */
inline fun Int.toBoolean(): Boolean = this == 1