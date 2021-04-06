package com.cornershop.counterstest.data.datasource.counter.local

import com.cornershop.counterstest.domain.model.counter.Counter

interface ICounterDataSourceLocal {
    fun updateCountersLocal(listCounters: List<Counter>)
    fun getCountersLocal(): List<Counter>
}