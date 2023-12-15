package com.tilikki.training.unimager.demo.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

fun <T> MutableList<T>.swapList(newList: List<T>) {
    clear()
    addAll(newList)
}

fun <T : Any> List<T>.toPagingDataFlow(): Flow<PagingData<T>> {
    return flowOf(PagingData.from(this))
}

fun <T : Any> LazyPagingItems<T>.isEmpty(): Boolean {
    return this.itemCount <= 0
}

fun CombinedLoadStates.getErrors(): LoadState.Error? {
    return this.source.append as? LoadState.Error
        ?: this.source.prepend as? LoadState.Error
        ?: this.source.refresh as? LoadState.Error
        ?: this.append as? LoadState.Error
        ?: this.prepend as? LoadState.Error
        ?: this.refresh as? LoadState.Error
}

@Composable
fun <T> rememberFlow(
    flow: Flow<T>,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
): Flow<T> {
    return remember(key1 = flow, key2 = lifecycleOwner) {
        flow.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }
}
