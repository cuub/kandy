package pt.hugofernandes.kandy.ktx

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

/**
 * Returns a [ByteArray] for the given [Bitmap]
 *
 * @param compressFormat [CompressFormat] to be used. Defaults to PNG
 * @param quality Quality level to be used. Defaults to 100
 */
fun Bitmap.getByteArray(
    compressFormat: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG,
    quality: Int = 100
): ByteArray {
    val stream = ByteArrayOutputStream()
    compress(compressFormat, quality, stream)
    return stream.toByteArray()
}