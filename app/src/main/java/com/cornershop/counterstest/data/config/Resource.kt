package com.cornershop.counterstest.data.config

import java.lang.Exception

sealed class Resource<out  T> {
    class Loading<out T>: Resource<T>()
    data class Success<out T>(val data: T): Resource<T>()
    data class Failure<out T>(val exception: Exception): Resource<T>()
}