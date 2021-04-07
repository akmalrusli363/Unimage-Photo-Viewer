package com.tilikki.training.unimager.demo.network

data class FetchResponse(
    val success: Boolean,
    val error: Throwable?
) {
    fun observeResponseStatus(
        onSuccess: () -> Unit,
        onError: (throwable: Throwable?) -> Unit,
        onUnhandledError: () -> Unit = { onError(null) }
    ) {
        if (!this.success) {
            if (this.error != null) {
                onError(this.error)
            } else {
                onUnhandledError()
            }
        } else {
            onSuccess()
        }
    }
}