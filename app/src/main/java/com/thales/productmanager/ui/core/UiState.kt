package com.thales.productmanager.ui.core

open class UiState(val errorDetail: ErrorDetail? = null)

data class ErrorDetail(val errorType: ErrorType, val errorCode: String = "", val errorMessage: String = "")

enum class ErrorType {
    INTERNAL, SERVER, NO_INTERNET, UNKNOWN
}

