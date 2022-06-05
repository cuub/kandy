package pt.hugofernandes.kandy

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import pt.hugofernandes.kandy.ktx.onReceive

class FlowyViewModel(coroutineDispatcher: CoroutineDispatcher) : ViewModel() {
    val numbers = flow {
        val data = mutableListOf<Int>()
        Numbers.asFlow()
            .onReceive(coroutineDispatcher) { data.add(it) }
            .join()
        data.forEach { emit(it) }
    }

    companion object {
        val Numbers = (0..10).toList()
    }
}
