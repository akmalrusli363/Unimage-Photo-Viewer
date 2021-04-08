package com.tilikki.training.unimager.demo.network

data class FetchResponse(
    val success: Boolean,
    val error: Throwable?
) {
    fun observeResponseStatus(
        onSuccess: () -> Unit,
        onError: (throwable: Throwable?) -> Unit,
        finally: () -> Unit? = {}
    ) {
        if (!this.success) {
            onError(this.error)
        } else {
            onSuccess()
        }
        finally()
    }
}