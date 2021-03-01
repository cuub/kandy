package pt.hugofernandes.kandy.ktx

import android.app.Activity
import android.content.Intent
import android.os.Bundle

inline fun <reified T : Activity> Activity.startActivity(options: Bundle? = null) {
    val intent = Intent()
    intent.setClass(this, T::class.java)
    startActivity(intent, options)
}