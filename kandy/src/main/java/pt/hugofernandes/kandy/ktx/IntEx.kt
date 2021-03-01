package pt.hugofernandes.kandy.ktx

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import androidx.core.content.ContextCompat

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Int.toColor(context: Context) = ContextCompat.getColor(context, this)

fun Int.toColorStateList() = ColorStateList.valueOf(this)

fun Int.toBoolean(): Boolean = this != 0