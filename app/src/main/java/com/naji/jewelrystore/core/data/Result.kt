package com.naji.jewelrystore.core.data

sealed class Result<T>(val data: T? = null, val msg: String? = null) {
    class Success<T>(data: T, msg: String? = null) : Result<T>(data, msg)
    class Failure<T>(msg: String, data: T? = null) : Result<T>(data, msg)
    class Loading<T>(data: T? = null) : Result<T>(data)
}