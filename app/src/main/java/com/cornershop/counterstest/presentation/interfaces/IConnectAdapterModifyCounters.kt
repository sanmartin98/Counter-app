package com.cornershop.counterstest.presentation.interfaces

import com.cornershop.counterstest.domain.model.counter.Counter

interface IConnectAdapterModifyCounters {
    fun increaseCounter(counter: Counter)
    fun decreaseCounter(counter: Counter)
}