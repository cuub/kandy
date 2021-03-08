package pt.hugofernandes.kandy.ktx

import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Injects a [ViewModel] in a [ViewGroup]
 *
 * Usage example in a ViewGroup:
 * val bla: BlaViewModel by viewModel()
 */
inline fun <reified T : ViewModel> ViewGroup.viewModel(): ReadOnlyProperty<ViewGroup, T> =
    object : ReadOnlyProperty<ViewGroup, T> {

        private var viewModel: T? = null

        override operator fun getValue(thisRef: ViewGroup, property: KProperty<*>): T =
            viewModel ?: getViewModel(thisRef).also { viewModel = it }

        private fun getViewModel(thisRef: ViewGroup): T {
            return (thisRef.context as FragmentActivity).getViewModel()
        }
    }