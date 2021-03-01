package pt.hugofernandes.kandy

import android.os.SystemClock
import android.view.View

class ProtectedClickListener(
    private val interval: Long = 250L,
    private val onProtectedClick: (View) -> Unit
) : View.OnClickListener {

    private var lastClick: Long = 0L

    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastClick < interval) {
            return
        }
        lastClick = SystemClock.elapsedRealtime()
        onProtectedClick(v)
    }
}