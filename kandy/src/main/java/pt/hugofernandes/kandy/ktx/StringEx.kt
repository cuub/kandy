package pt.hugofernandes.kandy.ktx

import android.annotation.SuppressLint

fun String.floatToInt() = this.toFloat().toInt()

@SuppressLint("DefaultLocale")
fun String.capitalizeWords() = split(" ").joinToString(" ") { it.toLowerCase().capitalize() }