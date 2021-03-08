package pt.hugofernandes.kandy.ktx

import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.toBitmap

/**
 * Tints the given [Drawable]
 *
 * @param tintColor Resource id of the color to use
 * @param tintMode [PorterDuff.Mode] to use. Defaults to SRC_IN
 */
fun Drawable.tinted(
    @ColorInt tintColor: Int? = null,
    tintMode: PorterDuff.Mode = PorterDuff.Mode.SRC_IN
) =
    apply {
        setTintList(tintColor?.toColorStateList())
        setTintMode(tintMode)
    }

/**
 * Returns a resized [Drawable] to the given [size] maintaining the aspect ratio
 *
 * @param resources
 * @param size The desired max width and height
 */
fun Drawable.resize(resources: Resources, size: Int): Drawable {
    val bitmap = this.toBitmap()
    val scaledBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val ratioX = size / bitmap.width.toFloat()
    val ratioY = size / bitmap.height.toFloat()
    val middleX = size / 2.0f
    val middleY = size / 2.0f
    val scaleMatrix = Matrix()
    scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)
    val canvas = Canvas(scaledBitmap)
    canvas.setMatrix(scaleMatrix)
    canvas.drawBitmap(
        bitmap,
        middleX - bitmap.width / 2,
        middleY - bitmap.height / 2,
        Paint(Paint.FILTER_BITMAP_FLAG)
    )
    return BitmapDrawable(resources, scaledBitmap)
}