package com.thales.productmanager.ui.core

open class UiState<T>(val data: T? = null, val errorDetail: ErrorDetail? = null)

data class ErrorDetail(val errorType: ErrorType, val errorCode: String = "", val errorMessage: String = "")

enum class ErrorType {
    INTERNAL, SERVER, NO_INTERNET, UNKNOWN
}

