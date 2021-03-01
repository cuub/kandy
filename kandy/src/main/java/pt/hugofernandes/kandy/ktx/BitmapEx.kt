package pt.hugofernandes.kandy.ktx

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

fun Bitmap.getByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}