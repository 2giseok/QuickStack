package com.project.api.exception

class QuickStackException : RuntimeException {

    val errorCode: QuickStackErrorCode
    val wrapRuntimeException: RuntimeException?
    val target: Any?
    private val _message: String

    fun print(): String = buildString {
        wrapRuntimeException?.message?.let { append(it) }
        append(_message)
        target?.let { append(" [").append(it).append("]") }
    }

    constructor(errorCode: QuickStackErrorCode, message: String) {
        this.errorCode = errorCode
        this.wrapRuntimeException = null
        this.target = null
        this._message = message
    }

    constructor(errorCode: QuickStackErrorCode) {
        this.errorCode = errorCode
        this.wrapRuntimeException = null
        this.target = null
        this._message = errorCode.message
    }

    constructor(errorCode: QuickStackErrorCode, target: Any) {
        this.errorCode = errorCode
        this.wrapRuntimeException = null
        this.target = target
        this._message = errorCode.message
    }

    constructor(errorCode: QuickStackErrorCode, wrapRuntimeException: RuntimeException) {
        this.errorCode = errorCode
        this.wrapRuntimeException = wrapRuntimeException
        this.target = null
        this._message = errorCode.message
    }

    constructor(errorCode: QuickStackErrorCode, wrapRuntimeException: RuntimeException, target: Any) {
        this.errorCode = errorCode
        this.wrapRuntimeException = wrapRuntimeException
        this.target = target
        this._message = errorCode.message
    }

    override val message: String get() = _message
}