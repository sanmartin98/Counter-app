package com.cornershop.counterstest.domain.usecase.counter

import com.cornershop.counterstest.data.dataaccess.Resource
import com.cornershop.counterstest.domain.model.counter.Counter

interface ICounterUseCase {
    suspend fun getCounters(): Resource<List<Counter>>
    suspend fun createCounter(titleCounter: Map<String, String>): Resource<List<Counter>>
    suspend fun incrementCounter(id: Map<String, String>): Resource<List<Counter>>
    suspend fun decrementCounter(id: Map<String, String>): Resource<List<Counter>>
    suspend fun deleteCounter(id: Map<String, String>): Resource<List<Counter>>
    fun updateCountersLocal(listCounters: List<Counter>)
    fun getCountersLocal(): List<Counter>
}