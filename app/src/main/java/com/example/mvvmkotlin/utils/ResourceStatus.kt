package com.example.mvvmkotlin.utils

data class ResourceStatus<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): ResourceStatus<T> =
            ResourceStatus(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): ResourceStatus<T> =
            ResourceStatus(status = Status.ERROR, data = data, message = message)

        fun <T> loading(data: T?): ResourceStatus<T> =
            ResourceStatus(status = Status.LOADING, data = data, message = null)
    }
}